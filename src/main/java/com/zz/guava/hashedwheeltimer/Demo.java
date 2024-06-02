package com.zz.guava.hashedwheeltimer;

import io.netty.util.HashedWheelTimer;

import java.util.concurrent.TimeUnit;

/**
 * @Author zhangzhen
 * @create 2024/5/31 17:46
 */
public class Demo {

    private int status;

    public static void main(String[] args) {
        HashedWheelTimer timer = new HashedWheelTimer();

        Runnable task = () -> System.out.println("aaa");

        timer.newTimeout(timeout -> task.run(), 1000, TimeUnit.MILLISECONDS);
    }

}
