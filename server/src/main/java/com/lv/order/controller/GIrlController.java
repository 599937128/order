package com.lv.order.controller;

import com.lv.order.config.GirlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: order
 * @Date: 2019/2/23 21:39
 * @Author: Mr.lv
 * @Description:
 */
@RestController
public class GIrlController {

    @Autowired
    private GirlConfig girlConfig;

    @GetMapping("/girl/print")
    public String print() {
        return "name" + girlConfig.getName() + "age" + girlConfig.getAge();
    }
}
