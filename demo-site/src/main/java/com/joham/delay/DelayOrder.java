package com.joham.delay;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 订单取消队列
 *
 * @author joham
 */
public class DelayOrder implements Delayed {

    private long orderId;

    private long startTime;

    /**
     * orderId:订单id
     * timeout:，取消时间(秒)
     */
    public DelayOrder(long orderId, int timeout) {
        this.orderId = orderId;
        this.startTime = System.currentTimeMillis() + timeout * 1000L;
    }

    @Override
    public int compareTo(Delayed other) {
        if (other == this) {
            return 0;
        }
        if (other instanceof DelayOrder) {
            DelayOrder otherRequest = (DelayOrder) other;
            long otherStartTime = otherRequest.getStartTime();
            return (int) (this.startTime - otherStartTime);
        }
        return 0;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(startTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (orderId ^ (orderId >>> 32));
        result = prime * result + (int) (startTime ^ (startTime >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DelayOrder other = (DelayOrder) obj;
        if (orderId != other.orderId)
            return false;
        if (startTime != other.startTime)
            return false;
        return true;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "DelayOrder [orderId=" + orderId + ", startTime=" + startTime + "]";
    }
}
