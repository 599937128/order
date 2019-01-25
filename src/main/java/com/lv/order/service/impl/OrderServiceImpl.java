package com.lv.order.service.impl;

import com.lv.order.dataobject.OrderMaster;
import com.lv.order.dto.OrderDTO;
import com.lv.order.enums.OrderStatusEnum;
import com.lv.order.enums.PayStatusEnum;
import com.lv.order.repository.OrderDetailRepository;
import com.lv.order.repository.OrderMasterRepository;
import com.lv.order.service.OrderService;
import com.lv.order.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        // 查询商品信息(调用商品服务)TODO
        // 计算总价 TODO
        // 扣库存 (调用商品服务) TODO
        //订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(KeyUtil.genUniqueKey());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(new BigDecimal(5));
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }
}
