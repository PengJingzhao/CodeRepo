package com.pjz.dubbo;

import com.pjz.dubbo.service.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @DubboReference(url = "dubbo://192.168.59.1:20880/com.pjz.dubbo.service.UserService")
    private UserService userService;

    @Test
    public void contextLoads() {

    }

    @Test
    public void testLogin() {
        boolean result = userService.login("admin", "123456");
        System.out.println("result=" + result);
    }
}
