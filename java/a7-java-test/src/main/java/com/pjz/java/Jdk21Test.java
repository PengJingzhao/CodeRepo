package com.pjz.java;

/**
 * Jdk21新特性：
 * 模式匹配
 * 虚拟线程: 更轻量级的线程模型
 */
public class Jdk21Test {
    public static void main(String[] args) {
        // 虚拟线程
        Thread.startVirtualThread(() -> System.out.println(Thread.currentThread().getName()));
    }
}
