package com.lv.order.message;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lv.order.utils.JsonUtil;
import com.lv.product.common.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @program: order
 * @Date: 2019/2/24 21:21
 * @Author: Mr.lv
 * @Description: 接受从商品服务传递的消息
 */
@Component
@Slf4j
@Transactional
public class ProductInfoReceiver {

    private static final String PRODUCT_STOCK_TEMPLATE = "product_stock_template";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void process(String message) {
        // message ->ProductInfoOutput
        List<ProductInfoOutput> productInfoOutputList = (List<ProductInfoOutput>) JsonUtil.fromJson(message,
                new TypeReference<List<ProductInfoOutput>>() {});
        log.info("c从队列【{}】接受消息: {}", "productInfo", productInfoOutputList);
        log.info("c从队列{}接受消息", productInfoOutputList);

        //存在到redis
        for (ProductInfoOutput productInfoOutput: productInfoOutputList) {
            stringRedisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLATE, productInfoOutput.getProductId()),
                    String.valueOf(productInfoOutput.getProductStock()));
            log.info("库存--{}",stringRedisTemplate.opsForValue().
                    get(String.format(PRODUCT_STOCK_TEMPLATE, productInfoOutput.getProductId())));
        }
    }
}
