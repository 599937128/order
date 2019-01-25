package com.lv.order.repository;

import com.lv.order.OrderApplicationTests;
import com.lv.order.dataobject.OrderMaster;
import com.lv.order.enums.OrderStatusEnum;
import com.lv.order.enums.PayStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @program: order
 * @Date: 2019/1/24 16:38
 * @Author: Mr.lv
 * @Description:
 */
@Component
public class OrderMasterRepositoryTest extends OrderApplicationTests {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Test
    public void testSave() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123456");
        orderMaster.setBuyerName("师兄");
        orderMaster.setBuyerAddress("北京");
        orderMaster.setBuyerPhone("13388867567");
        orderMaster.setOrderAmount(new BigDecimal(20));
        orderMaster.setBuyerOpenid("110110");
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        OrderMaster result = orderMasterRepository.save(orderMaster);
        Assert.assertTrue(result != null);
    }
}