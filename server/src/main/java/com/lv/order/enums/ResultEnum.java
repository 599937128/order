package com.lv.order.enums;

import lombok.Getter;

/**
 * @program: order
 * @Date: 2019/1/24 17:54
 * @Author: Mr.lv
 * @Description: 异常结果
 */
@Getter
public enum ResultEnum {
    PARAM_ERROR(1, "参数错误"),
    CART_EMPTY(2, "购物车为空"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
