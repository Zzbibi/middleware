package com.zz.io.aio;

/**
 * @Author zhangzhen
 * @create 2023/2/6 22:30
 */
public class TimeClient {

    public static void main(String[] args) {
        new Thread(new AsynTimeClientHandler("127.0.0.1", 8080), "AIO-AsynTimeClientHandler-001").start();
    }

}
