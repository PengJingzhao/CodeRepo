package com.pjz.job;

import java.util.Collection;

/**
 * 用于创建Scheduler实例的工厂
 */
public interface SchedulerFactory {

    /**
     * <p>
     * Returns a client-usable handle to a <code>Scheduler</code>.
     * </p>
     *
     */
    Scheduler getScheduler();

    /**
     * <p>
     * Returns a handle to the Scheduler with the given name, if it exists.
     * </p>
     */
    Scheduler getScheduler(String schedName);

    /**
     * <p>
     * Returns handles to all known Schedulers (made by any SchedulerFactory
     * within this jvm.).
     * </p>
     */
    Collection<Scheduler> getAllSchedulers();


}
