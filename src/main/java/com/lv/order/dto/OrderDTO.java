package com.lv.order.dto;

import com.lv.order.dataobject.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: order
 * @Date: 2019/1/24 17:18
 * @Author: Mr.Deng
 * @Description:
 */
@Data
public class OrderDTO {

    private String orderId;

    /**
     * 买家名字
     */
    private String buyerName;

    /**
     * 买家电话
     */
    private String buyerPhone;

    /**
     * 买家地址
     */
    private String buyerAddress;

    /**
     * 买家微信号Openid
     */
    private String buyerOpenid;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    /**
     * 订单状态默认0新下单
     */
    private Integer orderStatus;

    /**
     * 支付状态默认0未支付
     */
    private Integer payStatus;

    private List<OrderDetail> orderDetailList;
}
