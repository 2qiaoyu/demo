package com.joham.quartz;


import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;

/**
 * quartz定时器服务
 *
 * @author qiaoyu
 */

@Service
public class QuartzService {

    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    /**
     * 创建定时器并开始
     *
     * @param name
     * @param group
     * @param time
     * @throws Exception
     */
    public void startSchedule(String name, String group, String time) throws Exception {
        Scheduler scheduler = schedulerFactory.getScheduler();
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(name, group);
        // 如果不存在该trigger则创建一个
        if (null == trigger) {
            // 任务名，任务组，任务执行类
            JobDetail jobDetail = new JobDetail(name, group, Class.forName("com.joham.quartz.QuartzJob"));
            // 触发器名,触发器组
            trigger = new CronTrigger(name, group);
            // 触发器时间设定
            trigger.setCronExpression("00 18 14 07 10 ?");
            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            // Trigger已存在，那么更新相应的定时设置
            trigger.setCronExpression(time + " 14 07 10 ?");
            scheduler.rescheduleJob(trigger.getName(), trigger.getGroup(), trigger);
        }
        // 启动
        if (!scheduler.isShutdown()) {
            scheduler.start();
        }
    }
}
