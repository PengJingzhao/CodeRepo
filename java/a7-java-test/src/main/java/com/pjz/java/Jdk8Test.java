package com.pjz.java;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Jdk8新特性：
 * Lambda表达式：函数式编程，简化了代码书写
 * StreamApi: 流式Api，简化了集合操作，包括过滤，映射，收集
 * 新的日期Api：简化了日期操作，LocalDate，LocalTime。提供了更加现代化的日期时间操作方法。
 * 默认方法：接口可以通过default关键字，定义接口方法的默认实现
 *  Optional类：工具类，用于避免空指针异常
 */
public class Jdk8Test {
    public static void main(String[] args) {

        // lambda表达式
        Thread thread = new Thread(() -> System.out.println(Thread.currentThread().getName()));
        thread.start();

        // streamAPI
        ArrayList<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        List<Long> longs = strings.stream().map(Long::parseLong).collect(Collectors.toList());
        System.out.println(longs);

        // 日期Api
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        // 默认方法

        // Optional工具类
        Optional<String> optional = Optional.of("1");
        System.out.println(optional);
        optional.ifPresent(s -> System.out.println(s));
    }
}
