package com.lv.order.repository;

import com.lv.order.dataobject.OrderDetail;
import com.lv.order.dataobject.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @program: order
 * @Date: 2019/1/24 16:31
 * @Author: Mr.lv
 * @Description:
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    List<OrderDetail> findByOrderId(String orderId);
}
