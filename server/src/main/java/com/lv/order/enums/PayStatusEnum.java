package com.lv.order.enums;

import lombok.Getter;

/**
 * @program: order
 * @Date: 2019/1/24 16:52
 * @Author: Mr.Deng
 * @Description:
 */
@Getter
public enum PayStatusEnum {

    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功")
    ;

    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
