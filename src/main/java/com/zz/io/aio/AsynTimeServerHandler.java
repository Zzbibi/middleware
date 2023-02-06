package com.zz.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @Author zhangzhen
 * @create 2023/2/6 21:47
 */
public class AsynTimeServerHandler implements Runnable {

    private int port;
    public CountDownLatch countDownLatch;
    public AsynchronousServerSocketChannel asynServerSocketChannel;

    public AsynTimeServerHandler(int port) {
        this.port = port;
        try {
            asynServerSocketChannel = AsynchronousServerSocketChannel.open();
            asynServerSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("The time server is start in port:" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        countDownLatch = new CountDownLatch(1);
        doAccept();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void doAccept() {
        asynServerSocketChannel.accept(this, new AcceptCompletionHandler());
    }

}
