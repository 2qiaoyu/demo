package com.joham.listener;

import com.joham.delay.DelayOrder;
import com.joham.delay.DelayService;
import com.joham.order.Order;
import com.joham.order.OrderService;
import com.joham.util.RedisUtil;
import com.joham.util.ThreadPoolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.joham.order.OrderConst.DELAYORDER;


/**
 * 系统启动监听服务
 *
 * @author joham
 */
@Service
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * 日志记录
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(StartupListener.class);

    /**
     * 判断是否首次执行
     */
    private static boolean isStart = false;

    /**
     * 队列服务
     */
    @Autowired
    private DelayService delayService;

    /**
     * 订单服务
     */
    @Autowired
    private OrderService orderService;

    /**
     * redis
     */
    @Autowired
    private RedisUtil redisUtil;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent evt) {
        LOGGER.info("进入onApplicationEvent");
        if (evt.getApplicationContext().getParent() == null) {
            LOGGER.info("进入evt.getApplicationContext().getParent() == null");
            return;
        }

        if (!isStart) {
            isStart = true;

            //自动取消订单
            delayService.start(order -> {
                //异步来做
                ThreadPoolUtil.execute(() -> {
                    Long orderId = order.getOrderId();
                    //根据订单Id查询订单最新状态
                    Order order1 = orderService.getOrder(orderId);
                    //如果未支付订单
                    if (0 == order1.getOrderStatus()) {
                        LOGGER.info("系统自动取消订单" + orderId);
                        //取消订单
                        orderService.cancelOrder(orderId);
                        //删除redis订单记录
                        redisUtil.zRemove(DELAYORDER, orderId.toString());
                        LOGGER.info("系统自动取消订单后,删除redis记录" + orderId);
                    }
                });
            });

            //查找需要入队的订单
            ThreadPoolUtil.execute(() -> {
                LOGGER.info("查找需要入队的订单");
                //扫描redis，找到所有可能的orderId
                Set<String> values = redisUtil.zRange(DELAYORDER, 0, -1);
                if (values == null || values.size() <= 0) {
                    return;
                }
                LOGGER.info("需要入队的订单values：" + values);
                //写到DelayQueue,取消时间1分钟
                for (String value : values) {
                    DelayOrder order = new DelayOrder(Long.valueOf(value), 60);
                    LOGGER.info("读redis，value：" + value);
                    delayService.add(order);
                    LOGGER.info("订单自动入队：" + order.getOrderId());
                }
            });
        }
    }
}