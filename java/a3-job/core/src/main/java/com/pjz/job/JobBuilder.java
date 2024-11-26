package com.pjz.job;

import com.pjz.job.impl.JobDetailImpl;

/**
 * 用于构建JobDetail实例的构造器
 */
public class JobBuilder {

    private JobKey key;
    private Class<? extends Job> jobClass;
    private JobDataMap jobDataMap = new JobDataMap();

    public static JobBuilder newJob(Class<? extends Job> jobClass) {
        JobBuilder b = new JobBuilder();
        b.ofType(jobClass);
        return b;
    }

    public JobBuilder withIdentity(JobKey key) {
        this.key = key;
        return this;
    }

    public JobDetail build() {
        JobDetailImpl job = new JobDetailImpl();
        return job;
    }

    public JobBuilder ofType(Class<? extends Job> jobClass) {
        this.jobClass = jobClass;
        return this;
    }

    public JobBuilder usingJobData(String key, String value) {
        this.jobDataMap.put(key, value);
        return this;
    }
}
