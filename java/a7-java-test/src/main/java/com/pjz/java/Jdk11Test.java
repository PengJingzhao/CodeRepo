package com.pjz.java;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.temporal.Temporal;
import java.util.ArrayList;

/**
 * Jdk11新特性:
 * var: 用于局部变量
 * 新的Http客户端Api：Http2.0和WebSocket
 * 增强字符串操作：字符串新增了一些操作，isBlanks,lines,repeat
 */
public class Jdk11Test {
    public static void main(String[] args) {
        // var
        var lists = new ArrayList<String>();
        System.out.println(lists);

        // 新的http客户端
        // 创建http客户端
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.baidu.com/"))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }


        // 增强字符串操作
        String string = new String("test");
        boolean isBlank = string.isBlank();
        System.out.println(isBlank);

        String repeat = string.repeat(10);
        System.out.println(repeat);

        string.lines().forEach(System.out::println);


    }
}
