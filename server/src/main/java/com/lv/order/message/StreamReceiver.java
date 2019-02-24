package com.lv.order.message;

import com.lv.order.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * @program: order
 * @Date: 2019/2/24 15:03
 * @Author: Mr.lv
 * @Description:
 */
@Slf4j
@Component
@EnableBinding(StreamClient.class)
public class StreamReceiver {

//    @StreamListener(StreamClient.INPUT2)
//    public void process(OrderDTO message) {
//        log.info("StreamReceiver: {}", message);
//        log.info("StreamReceiver: {}", "进来了");
//    }

    /**
     * 接受orderdto消息
     * @param message
     */
    @StreamListener(StreamClient.INPUT)
    @SendTo(StreamClient.INPUT2)  // 回应消息
    public String process(OrderDTO message) {
        log.info("StreamReceiver: {}", message);
        return "received";
    }

    @StreamListener(StreamClient.INPUT2)
    public void process2(String message) {
        log.info("StreamReceiver2: {}", message);
    }
}
