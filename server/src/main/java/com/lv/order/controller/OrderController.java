package com.lv.order.controller;

import com.lv.order.VO.ResultVO;
import com.lv.order.VO.ResultVOUtil;
import com.lv.order.converter.OrderForm2OrderDTOConverter;
import com.lv.order.dto.OrderDTO;
import com.lv.order.enums.ResultEnum;
import com.lv.order.exception.OrderException;
import com.lv.order.form.OrderForm;
import com.lv.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: order
 * @Date: 2019/1/24 17:13
 * @Author: Mr.Deng
 * @Description:
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    /**
     * 1.参数校验
     * 2.查询商品信息(调用商品服务)
     * 3.计算总价
     * 4.扣库存(调用商品服务)
     * 5.订单入库
     */
    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResultVO<Map<String,String>> creat(@Valid OrderForm orderForm,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("[创建订单] 参数不正确, orderForm={}", orderForm);
            throw new OrderException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        // 转化 orderForm --> orderDTO
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("[创建订单]购物车信息为空");
            throw new OrderException(ResultEnum.CART_EMPTY);
        }
        OrderDTO result = orderService.create(orderDTO);

        Map<String,String> map = new HashMap<>();
        map.put("orderId", orderDTO.getOrderId());
        return ResultVOUtil.success(map);
    }
}
