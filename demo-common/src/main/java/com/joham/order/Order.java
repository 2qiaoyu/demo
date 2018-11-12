package com.joham.order;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单
 *
 * @author joham
 */
@Data
public class Order {

    /**
     * 订单Id
     */
    private Long orderId;

    /**
     * 订单号
     */
    private String orderCode;

    /**
     * 订单金额
     */
    private BigDecimal orderPrice;

    /**
     * 订单状态 0待付款1已付款2已取消
     */
    private Integer orderStatus;
}
