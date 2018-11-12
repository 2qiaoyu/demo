package com.joham.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单服务
 *
 * @author joham
 */
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 根据订单Id查询订单
     *
     * @param orderId 订单Id
     * @return
     */
    public Order getOrder(Long orderId) {
        return orderMapper.getOrder(orderId);
    }

    /**
     * 订单取消
     *
     * @param orderId 订单Id
     * @return
     */
    public int cancelOrder(Long orderId) {
        return orderMapper.cancelOrder(orderId);
    }

    /**
     * 创建订单
     * @param order
     * @return
     */
    public int createOrder(Order order) {
        return orderMapper.createOrder(order);
    }
}
