package com.lv.order.service.impl;
import com.lv.order.dataobject.OrderDetail;
import com.lv.order.enums.ResultEnum;
import com.lv.order.exception.OrderException;
import com.lv.product.common.*;
import com.lv.order.dataobject.OrderMaster;
import com.lv.order.dto.OrderDTO;
import com.lv.order.enums.OrderStatusEnum;
import com.lv.order.enums.PayStatusEnum;
import com.lv.order.repository.OrderDetailRepository;
import com.lv.order.repository.OrderMasterRepository;
import com.lv.order.service.OrderService;
import com.lv.order.utils.KeyUtil;
import com.lv.product.client.ProductClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @program: order
 * @Date: 2019/1/24 17:21
 * @Author: Mr.lv
 * @Description:
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductClient productClient;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        //orderId
        String orderId = KeyUtil.genUniqueKey();
        // 查询商品信息(调用商品服务)

        List<String> productIdList = orderDTO.getOrderDetailList().stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());
        List<ProductInfoOutput> productInfoOutputs = productClient.listForOrder(productIdList);
        // 计算总价
        BigDecimal orderAmount = new BigDecimal(String.valueOf(BigDecimal.ZERO));
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            // 单价*数量
            for (ProductInfoOutput productInfo : productInfoOutputs) {
                if (productInfo.getProductId().equals(orderDetail.getProductId())) {
                     orderAmount = productInfo.getProductPrice().
                             multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmount);
                     BeanUtils.copyProperties(productInfo, orderDetail);
                     orderDetail.setOrderId(orderId);
                     orderDetail.setDetailId(KeyUtil.genUniqueKey());

                     // 订单详情入库
                    orderDetailRepository.save(orderDetail);
                }
            }

        }
        // 扣库存 (调用商品服务)
        List<DecreaseStockInput> decreaseStockInputList = orderDTO.getOrderDetailList().stream()
                .map(e -> new DecreaseStockInput(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productClient.decreseStock(decreaseStockInputList);

        //订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }

    /**
     * 完结订单（只能卖家操作）
     *
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public OrderDTO finish(String orderId) {

        // 1.先查询订单
        Optional<OrderMaster> orderMasterOptional = orderMasterRepository.findById(orderId);

        if (!orderMasterOptional.isPresent()) {
            throw new OrderException(ResultEnum.ORDER_NOT_EXIST);
        }
        // 2.判断订单状态
        OrderMaster orderMaster = orderMasterOptional.get();
        if (OrderStatusEnum.NEW.getCode() != orderMaster.getOrderStatus()) {
            throw new OrderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 3.修改订单状态为完结
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderMasterRepository.save(orderMaster);

        //查询订单详情
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new OrderException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
