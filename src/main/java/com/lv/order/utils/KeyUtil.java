package com.lv.order.utils;

import java.util.Random;

/**
 * @program: order
 * @Date: 2019/1/24 17:31
 * @Author: Mr.lv
 * @Description:
 */
public class KeyUtil {

    /**
     * 生成唯一的主键
     * 格式 : 时间戳 + 随机数
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
