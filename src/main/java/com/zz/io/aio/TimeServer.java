package com.zz.io.aio;

/**
 * @Author zhangzhen
 * @create 2023/2/6 21:46
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = 8080;
        AsynTimeServerHandler timeServerHandler = new AsynTimeServerHandler(port);
        new Thread(timeServerHandler, "AIO-AsynTimeServerHandler-001").start();
    }

}
