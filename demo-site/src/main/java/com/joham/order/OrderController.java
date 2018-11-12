package com.joham.order;

import com.joham.delay.DelayOrder;
import com.joham.delay.DelayService;
import com.joham.util.RedisUtil;
import com.joham.util.ThreadPoolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.joham.order.OrderConst.DELAYORDER;

@RestController
@RequestMapping("order")
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    /**
     * 订单服务
     */
    @Autowired
    private OrderService orderService;

    /**
     * 队列
     */
    @Autowired
    private DelayService delayService;

    /**
     * redis
     */
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 创建订单
     *
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public Long createOrder() {
        Order order = new Order();
        Random random = new Random();
        order.setOrderCode("20181109" + random.nextInt(10000));
        order.setOrderPrice(BigDecimal.valueOf(100));
        order.setOrderStatus(0);
        int num = orderService.createOrder(order);
        //创建订单成功
        if (num == 1) {
            Long orderId = order.getOrderId();
            //把订单插入到取消订单的队列和redis
            ThreadPoolUtil.execute(() -> {

                //1 插入到取消订单的队列
                DelayOrder delayOrder = new DelayOrder(orderId, 60);
                delayService.add(delayOrder);
                logger.info("订单加入队列" + orderId);
                //2 加入redis
                redisUtil.zAdd(DELAYORDER, orderId.toString(), orderId.doubleValue());
                logger.info("订单写入redis" + orderId);
                //判断失效时间
                if (redisUtil.getExpire(DELAYORDER) == -1) {
                    //4 redis键失效时间设为1天
                    redisUtil.expire(DELAYORDER, 12 * 60L, TimeUnit.MINUTES);
                }
            });
        }
        return order.getOrderId();
    }
}
