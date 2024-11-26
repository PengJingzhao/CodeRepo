package com.pjz.job;

import com.pjz.job.spi.MutableTrigger;

public class SimpleScheduleBuilder extends ScheduleBuilder<SimpleTrigger>{
    @Override
    protected MutableTrigger build() {
        return null;
    }

    public static SimpleScheduleBuilder simpleSchedule() {
        return new SimpleScheduleBuilder();
    }
}
