package com.lv.order.controller;

import com.lv.product.client.ProductClient;
import com.lv.product.common.DecreaseStockInput;
import com.lv.product.common.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @program: order
 * @Date: 2019/1/25 10:25
 * @Author: Mr.Deng
 * @Description:
 */
@RestController
@Slf4j
public class ClientController {

    @Autowired
    private ProductClient productClient;

    @GetMapping("/getProductMsg")
    public String getProductMsg() {
        String response = productClient.productMsg();
        log.info("response={}", response);
        return response;
    }

    @GetMapping("/getProductList")
    public String getProductList() {
        List<ProductInfoOutput> productInfoList = productClient.listForOrder(Arrays.asList("164103465734242707"));
        log.info("response={}", productInfoList);
        return "ok";
    }

    @GetMapping("/productDecreseStock")
    public String productDecreseStock() {
        productClient.decreseStock(Arrays.asList(new DecreaseStockInput("164103465734242707", 3)));
        return "ok";
    }

}
