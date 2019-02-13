package com.lv.order.service.impl;
import com.lv.order.dataobject.OrderDetail;
import com.lv.product.common.*;
import com.lv.order.dataobject.OrderMaster;
import com.lv.order.dataobject.ProductInfo;
import com.lv.order.dto.CartDTO;
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
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
}
