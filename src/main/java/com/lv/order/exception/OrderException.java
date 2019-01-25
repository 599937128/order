package com.lv.order.exception;

import com.lv.order.enums.ResultEnum;

/**
 * @program: order
 * @Date: 2019/1/24 17:50
 * @Author: Mr.Deng
 * @Description:
 */
public class OrderException extends RuntimeException {

    private Integer code;

    public OrderException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public OrderException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
