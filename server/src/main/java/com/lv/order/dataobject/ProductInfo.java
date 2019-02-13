package com.lv.order.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: product
 * @Date: 2019/1/24 11:18
 * @Author: Mr.lv
 * @Description:
 */
//@Table(name = "xxxx")
@Data
@Entity
public class ProductInfo {

    @Id
    private String productId;

    /**
     * 商品名
     */
    private String productName;
    /**
     * 单价
     */
    private BigDecimal productPrice;
    /**
     * 库存
     */
    private Integer productStock;
    /**
     * 描述
     */
    private String productDescription;
    /**
     * 图片
     */
    private String productIcon;
    /**
     * 状态
     */
    private Integer productStatus;
    /**
     * 分类
     */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;
}
