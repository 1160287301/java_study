package com.test;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class TestDemo {
    public static void main(String[] args) {
        int count = 30;
        System.out.println("当前线程: " + Thread.currentThread().getName() + " 卖出第 " + (count--) + " 张票,还剩: " + count);

        List list = new ArrayList();
        list.forEach(System.out::println);

    }
}
