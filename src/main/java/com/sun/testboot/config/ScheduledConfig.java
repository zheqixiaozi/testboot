package com.sun.testboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
/**
 * 配置定时任务<br>
 * 1）当定时任务很多的时候，为了提高任务执行效率，可以采用并行方式执行定时任务，任务之间互不影响，只要实现SchedulingConfigurer接口就可以。<br>
 * 2）这里指定用3个线程来并行处理
 * **/
@Configuration
public class ScheduledConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(Executors.newScheduledThreadPool(3));
    }


}
