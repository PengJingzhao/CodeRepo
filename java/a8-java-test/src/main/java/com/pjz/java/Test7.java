package com.pjz.java;

public class Test7 {

    public static int TEST = 1;

    static {
        TEST = 10;
    }

    public static void main(String[] args) {
        System.out.println(TEST);
    }
}
