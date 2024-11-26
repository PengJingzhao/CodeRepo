package com.pjz.job.impl;

import com.pjz.job.Scheduler;
import com.pjz.job.SchedulerFactory;
import com.pjz.job.utils.PropertiesParser;

import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class StdSchedulerFactory implements SchedulerFactory {

    private PropertiesParser cfg;

    public static final String PROPERTIES_FILE = "org.quartz.properties";
    public static final String PROP_SCHED_INSTANCE_NAME = "org.quartz.scheduler.instanceName";

    private String propSrc = null;

    /**
     * Create an uninitialized StdSchedulerFactory.
     */
    public StdSchedulerFactory() {
    }

    @Override
    public Scheduler getScheduler() {
        //初始化
        if (cfg == null) {
            initialize();
        }

        //获取scheduler HashMap对象
        SchedulerRepository schedulerRepository = SchedulerRepository.getInstance();

        //已存在的scheduler不用创建


        return null;
    }

    @Override
    public Scheduler getScheduler(String schedName) {
        return null;
    }

    @Override
    public Collection<Scheduler> getAllSchedulers() {
        return List.of();
    }

    /**
     * Initialize the <code>{SchedulerFactory}</code> with
     * the contents of a <code>Properties</code> file and overriding System
     * properties.
     */
    public void initialize() {
        // short-circuit if already initialized
        if (cfg != null) {
            return;
        }

        String requestedFile = System.getProperty(PROPERTIES_FILE);
        String propFileName = requestedFile != null ? requestedFile
                : "quartz.properties";
        File propFile = new File(propFileName);

        Properties props = new Properties();

        InputStream in = null;

        try {
            if (propFile.exists()) {
                try {
                    if (requestedFile != null) {
                        propSrc = "specified file: '" + requestedFile + "'";
                    } else {
                        propSrc = "default file in current working dir: 'quartz.properties'";
                    }

                    in = new BufferedInputStream(new FileInputStream(propFileName));
                    props.load(in);

                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            } else if (requestedFile != null) {
                in =
                        Thread.currentThread().getContextClassLoader().getResourceAsStream(requestedFile);

                propSrc = "specified file: '" + requestedFile + "' in the class resource path.";

                in = new BufferedInputStream(in);
                try {
                    props.load(in);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else {
                propSrc = "default resource file in Quartz package: 'quartz.properties'";

                ClassLoader cl = getClass().getClassLoader();


                in = cl.getResourceAsStream(
                        "quartz.properties");

                if (in == null) {
                    in = cl.getResourceAsStream(
                            "/quartz.properties");
                }
                if (in == null) {
                    in = cl.getResourceAsStream(
                            "org/quartz/quartz.properties");
                }
                if (in == null) {

                }
                try {
                    props.load(in);
                } catch (IOException ioe) {

                }
            }
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignore) { /* ignore */ }
            }
        }
    }

    private String getSchedulerName() {
        return cfg.getStringProperties(PROP_SCHED_INSTANCE_NAME, "QuartzScheduler");
    }
}
