package com.lv.order.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lv.order.dataobject.OrderDetail;
import com.lv.order.dto.OrderDTO;
import com.lv.order.enums.ResultEnum;
import com.lv.order.exception.OrderException;
import com.lv.order.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: order
 * @Date: 2019/1/24 17:58
 * @Author: Mr.lv
 * @Description: 对象转化
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        Gson gson = new Gson();
        try{
            orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>(){}.
                            getType());
        }catch (Exception e) {
            log.error("[json转换] 错误, String={}", orderForm.getItems());
            throw new OrderException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
