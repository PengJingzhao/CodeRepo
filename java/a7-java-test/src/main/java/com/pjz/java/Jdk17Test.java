package com.pjz.java;

/**
 * Jdk17新特性：
 * 密封类：直接用密封类来指定允许继承的子类列表
 * 增强Switch表达式：case项可以使用lambda表达式,箭头语法，yield关键字返回值并跳出当前switch语块
 * 文本块：三引号+多行文本
 */
public class Jdk17Test {

    // 密封类
    public sealed class Animal permits Dog, Cat {

    }

    public final class Dog extends Animal {

    }

    public final class Cat extends Animal {

    }

    public static void main(String[] args) {
        // 增强 switch 表达式
        String input = "1";
        switch (input) {
            case "1" -> System.out.println(1);
            case "2" -> System.out.println(2);
            default -> throw new IllegalArgumentException("输入参数错误");
        }

        // 文本块
        String json = """
                {
                    "username": "zs"
                }
                """;
        System.out.println(json);
    }
}
