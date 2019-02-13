package com.lv.order.repository;

import com.lv.order.dataobject.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @program: order
 * @Date: 2019/1/24 16:31
 * @Author: Mr.lv
 * @Description:
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {


}
