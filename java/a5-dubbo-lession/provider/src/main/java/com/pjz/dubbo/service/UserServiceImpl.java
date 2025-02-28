package com.pjz.dubbo.service;

public class UserServiceImpl implements UserService {
    @Override
    public boolean login(String name, String password) {
        System.out.println("UserServiceImpl.login name=" + name + " ,password=" + password);
        return false;
    }
}
