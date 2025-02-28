package com.pjz.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

public class ProviderApplication {
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-provider.xml");
        applicationContext.start();

        new CountDownLatch(1).await();
    }
}
