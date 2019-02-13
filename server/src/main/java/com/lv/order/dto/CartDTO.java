package com.lv.order.dto;

import lombok.Data;

/**
 * @program: product
 * @Date: 2019/1/25 14:39
 * @Author: Mr.lv
 * @Description: 商品dto
 */
@Data
public class CartDTO {

    /**
     * 商品id
     */
    private String productId;

    /**
     * 商品数量
     */
    private Integer productQuantity;

    public CartDTO() {
    }

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
