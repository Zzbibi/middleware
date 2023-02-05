package com.zz.io.nio;

/**
 * @Author zhangzhen
 * @create 2023/2/5 21:09
 */
public class TimeClient {

    public static void main(String[] args) {
        new Thread(new TimeClientHandle("127.0.0.1", 8080)).start();
    }

}
