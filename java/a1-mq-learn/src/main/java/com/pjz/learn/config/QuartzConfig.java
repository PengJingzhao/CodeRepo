package com.pjz.learn.config;

import com.pjz.learn.entity.FirstJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    public static final String ID = "SUMMERDAY";

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder
                .newJob(FirstJob.class)
                .withIdentity(ID + "01")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger trigger() {

        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(5)
                .repeatForever();

        return TriggerBuilder
                .newTrigger()
                .forJob(jobDetail())
                .withIdentity(ID + "Trigger01")
                .withSchedule(scheduleBuilder)
                .build();
    }

}
