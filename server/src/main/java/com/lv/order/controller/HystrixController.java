package com.lv.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * @program: order
 * @Date: 2019/2/28 16:13
 * @Author: Mr.lv
 * @Description: Hystrix 使用
 */
@RestController
public class HystrixController {

    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping("/getProductInfoList")
    public String getProductInfoList() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject("http://127.0.0.1:8081/product/listForOrder",
                Arrays.asList("157875196366160022"),
                String.class);
        // 也可以自定义异常然后触发faalback()
//        throw  new RuntimeException("出现异常了");
    }

    private String fallback() {
        return "太拥挤了请稍后再试~~";
    }
}
