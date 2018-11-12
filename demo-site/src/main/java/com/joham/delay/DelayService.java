package com.joham.delay;

import com.joham.util.ThreadPoolUtil;
import org.springframework.stereotype.Service;

import java.util.concurrent.DelayQueue;

/**
 * 队列服务
 *
 * @author joham
 */

@Service
public class DelayService {

    private boolean start;
    private OnDelayedListener listener;
    private DelayQueue<DelayOrder> delayQueue = new DelayQueue<DelayOrder>();

    public interface OnDelayedListener {
        /**
         * 队列时间到达
         *
         * @param order
         */
        void onDelayedArrived(DelayOrder order);
    }

    public void start(OnDelayedListener listener) {
        if (start) {
            return;
        }
        start = true;
        this.listener = listener;
        ThreadPoolUtil.execute(() -> {
            try {
                while (true) {
                    DelayOrder order = delayQueue.take();
                    if (DelayService.this.listener != null) {
                        DelayService.this.listener.onDelayedArrived(order);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void add(DelayOrder order) {
        delayQueue.put(order);
    }

    public boolean remove(DelayOrder order) {
        return delayQueue.remove(order);
    }

    public void add(long orderId) {
        delayQueue.put(new DelayOrder(orderId, 30));
    }

    public void remove(long orderId) {
        DelayOrder[] array = delayQueue.toArray(new DelayOrder[]{});
        if (array == null || array.length <= 0) {
            return;
        }
        DelayOrder target = null;
        for (DelayOrder order : array) {
            if (order.getOrderId() == orderId) {
                target = order;
                break;
            }
        }
        if (target != null) {
            delayQueue.remove(target);
        }
    }
}
