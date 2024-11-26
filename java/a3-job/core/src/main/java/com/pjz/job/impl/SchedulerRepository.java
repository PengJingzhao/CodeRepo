package com.pjz.job.impl;

import com.pjz.job.Scheduler;

import java.util.HashMap;

public class SchedulerRepository {

    private final HashMap<String, Scheduler> schedulers;

    public SchedulerRepository() {
        this.schedulers = new HashMap<>();
    }

    //单例模式
    //线程安全：使用静态类，Java 的类加载机制保证了类的静态初始化过程是线程安全的，因此不需要显式地进行同步
    //私有的静态内部类 Holder。在 Java 中，静态内部类只有在第一次使用时才会被加载
    private static class Holder {
        //final 关键字保证这个实例在类加载时就被初始化，并且无法改变引用
        private static final SchedulerRepository INSTANCE = new SchedulerRepository();
    }

    //当调用 getInstance() 方法时，Holder.INSTANCE 被访问，此时 Holder 类被加载，INSTANCE 被初始化
    public static SchedulerRepository getInstance() {
        return Holder.INSTANCE;
    }
}
