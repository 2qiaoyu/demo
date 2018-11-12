package com.joham.util;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工具类
 *
 * @author joham
 */
public class ThreadPoolUtil {

    /**
     * 线程池核心线程数
     */
    private static int CORE_POOL_SIZE = 8;

    /**
     * 线程池最大线程数
     */
    private static int MAX_POOL_SIZE = 32;

    /**
     * 额外线程空状态生存时间
     */
    private static long KEEP_ALIVE_TIME = 600;

    private static TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    /**
     * 阻塞队列。当核心线程都被占用，且阻塞队列已满的情况下，才会开启额外线程。
     */
    private static BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(4);

    /**
     * 线程池
     */
    private static ThreadPoolExecutor threadPool;

    /**
     * 线程工厂
     */
    private static ThreadFactory threadFactory = new ThreadFactory() {
        private final AtomicInteger integer = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "ThreadPoolUtil thread:" + integer.getAndIncrement());
        }
    };

    static {
        threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TIME_UNIT, workQueue, threadFactory);
    }

    /**
     * 任务加入线程池并执行
     *
     * @param runnable
     */
    public static void execute(Runnable runnable) {
        threadPool.execute(runnable);
    }

    /**
     * 线程池中线程数目
     *
     * @return
     */
    public static int getPoolSize() {
        return threadPool.getPoolSize();
    }

    /**
     * 队列中等待执行的任务数目
     *
     * @return
     */
    public static int getQueueSize() {
        return threadPool.getQueue().size();
    }

    /**
     * 已执行完毕的任务数目
     *
     * @return
     */
    public static long getCompletedTaskCount() {
        return threadPool.getCompletedTaskCount();
    }

    /**
     * 线程池容量的动态调整
     *
     * @param corePoolSize    线程池核心线程数
     * @param maximumPoolSize 线程池最大线程数
     * @param time            额外线程空状态生存时间
     * @param unit            时间单位
     */
    public static void setPoolSize(Integer corePoolSize, Integer maximumPoolSize, Long time, TimeUnit unit) {

        setCorePoolSize(corePoolSize);

        setMaximumPoolSize(maximumPoolSize);

        setKeepAliveTime(time, unit);

    }

    private static void setCorePoolSize(Integer corePoolSize) {
        if (corePoolSize != null) {
            CORE_POOL_SIZE = corePoolSize;
            threadPool.setCorePoolSize(corePoolSize);
        }
    }

    private static void setMaximumPoolSize(Integer maximumPoolSize) {
        if (maximumPoolSize != null) {
            MAX_POOL_SIZE = maximumPoolSize;
            threadPool.setMaximumPoolSize(maximumPoolSize);
        }
    }

    private static void setKeepAliveTime(Long time, TimeUnit unit) {
        if (time != null) {
            KEEP_ALIVE_TIME = time;
            if (unit == null) {
                unit = TIME_UNIT;
            }
            threadPool.setKeepAliveTime(time, unit);
        }
    }

}
