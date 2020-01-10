package com.study;

public class TestDemo {
    public static void main(String[] args) {
        int count = 30;
        System.out.println("当前线程: " + Thread.currentThread().getName() + " 卖出第 " + (count--) + " 张票,还剩: " + count);

    }
}
