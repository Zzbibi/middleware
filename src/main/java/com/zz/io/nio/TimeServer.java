package com.zz.io.nio;

/**
 * @Author zhangzhen
 * @create 2023/2/5 20:40
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = 8080;
        new Thread(new MultiplexerTimeServer(port), "NIO-MultiplexerTimeServer-001").start();
    }

}
