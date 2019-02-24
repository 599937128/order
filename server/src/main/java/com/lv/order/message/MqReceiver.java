package com.lv.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @program: order
 * @Date: 2019/2/24 13:54
 * @Author: Mr.lv
 * @Description: 接受mq消息
 */
@Slf4j
@Component
public class MqReceiver {

//    @RabbitListener(queues = "MyQueue")
    //2 自动创建队列
//    @RabbitListener(queuesToDeclare = @Queue("MyQueue"))
//    自动创建队列，并且Exchange和Queue绑定
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("MyQueue"),
            exchange = @Exchange("MyExchange")
    ))
    public void process(String message) {
        log.info("MqReceiver: {}", message);
    }

    /**
     * 数码供应商接收消息 做不同的分组
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key = "computer",
            value = @Queue("computerOrder")
    ))
    public void processComputer(String message) {
        log.info("computer MqReceiver: {}", message);
    }

    /**
     * 水果供应商接收消息
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key = "fruit",
            value = @Queue("fruitOrder")
    ))
    public void processFruit(String message) {
        log.info("fruit MqReceiver: {}", message);
    }
}
