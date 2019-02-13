package com.lv.order.VO;

import lombok.Data;

/**
 * @program: order
 * @Date: 2019/1/25 9:43
 * @Author: Mr.lv
 * @Description:
 */
@Data
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T data;
}
