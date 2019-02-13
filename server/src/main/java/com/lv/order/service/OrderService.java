package com.lv.order.service;

import com.lv.order.dto.OrderDTO;

/**
 * @program: order
 * @Date: 2019/1/24 17:16
 * @Author: Mr.lv
 * @Description:
 */
public interface OrderService {

    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    OrderDTO create(OrderDTO orderDTO);
}
