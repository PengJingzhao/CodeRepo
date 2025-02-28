package com.pjz.java;

public class Test8 {

    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> clazz = Class.forName("com.pjz.java.Demo1");
    }

}

class Demo1 {

    static {
        System.out.println("触发类的初始化");
    }
}
