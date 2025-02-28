package com.pjz.dubbo;

import com.pjz.dubbo.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class ConsumerApplication {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-consumer.xml");
        UserService userService = (UserService) applicationContext.getBean("userService");
        boolean result = userService.login("admin", "123456");
        System.out.println("the result of login: " + result);

        System.in.read();
    }
}
