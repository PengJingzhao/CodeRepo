package com.pjz.job;

import com.pjz.job.spi.MutableTrigger;

import java.util.Date;

/**
 * 用于构建Trigger实例的构造器
 */
public class TriggerBuilder<T extends Trigger> {

    private TriggerKey key;
    private Date startTime = new Date();
    private ScheduleBuilder<?> scheduleBuilder = null;

    private TriggerBuilder() {

    }

    public static TriggerBuilder<Trigger> newTrigger() {
        return new TriggerBuilder<>();
    }

    public TriggerBuilder<T> withIdentity(String key, String group) {
        this.key = new TriggerKey(key, group);
        return this;
    }

    public TriggerBuilder<T> startNow() {
        this.startTime = new Date();
        return this;
    }

    public T build() {
        if (scheduleBuilder == null) {
            this.scheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
        }
        MutableTrigger trig = this.scheduleBuilder.build();
        return (T) trig;
    }

}
