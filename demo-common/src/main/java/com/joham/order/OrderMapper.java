package com.joham.order;

import org.springframework.stereotype.Repository;

/**
 * 订单
 *
 * @author joham
 */
@Repository
public interface OrderMapper {

    /**
     * 根据订单Id查询订单
     *
     * @param orderId 订单Id
     * @return
     */
    Order getOrder(Long orderId);

    /**
     * 订单取消
     *
     * @param orderId 订单Id
     * @return
     */
    int cancelOrder(Long orderId);

    /**
     * 创建订单
     *
     * @param order 订单
     * @return
     */
    int createOrder(Order order);
}
