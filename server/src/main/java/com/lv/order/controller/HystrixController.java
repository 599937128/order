package com.lv.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@DefaultProperties(defaultFallback = "defaultFallback")
public class HystrixController {

//    @HystrixCommand(fallbackMethod = "fallback")
    //超时设置
//    @HystrixCommand(commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
//            value = "3000")
//    })
    //熔断设置
//    @HystrixCommand(commandProperties = {
//            // 设置熔断
//          @HystrixProperty(name = "circuitBreaker.enabled",
//          value = "true"),
//          @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",
//          value = "10"),
//          @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",
//          value = "10000"),
//          @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",
//          value = "60")
//    })
    @HystrixCommand
    @GetMapping("/getProductInfoList")
    public String getProductInfoList(@RequestParam("number") Integer number) {
        if (number % 2 == 0){
            return "success";
        }
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

    private String defaultFallback() {
        return "默认配置---太拥挤了请稍后再试~~";
    }
}
