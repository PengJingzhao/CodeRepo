package com.pjz.dubbo.service;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@DubboService
@Service
public class UserServiceImpl implements UserService {
    @Override
    public boolean login(String name, String password) {
        System.out.println("provider2.userServiceImpl.login name=" + name + ",password=" + password);
        return false;
    }
}
