package com.pjz.job;

import com.pjz.job.spi.MutableTrigger;

public abstract class ScheduleBuilder<T extends Trigger> {

    protected abstract MutableTrigger build();

}
