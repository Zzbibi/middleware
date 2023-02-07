package com.zz.io.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * 接收连接成功之后的回调
 *
 * @Author zhangzhen
 * @create 2023/2/6 21:51
 */
public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsynTimeServerHandler> {

    @Override
    public void completed(AsynchronousSocketChannel asynchronousSocketChannel, AsynTimeServerHandler attachment) {
        // 继续调用accept()方法，接收其他客户端连接
        attachment.asynServerSocketChannel.accept(attachment, this);

        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        /**
         * ByteBuffer: 接收缓冲区，用于从异步channel中读取数据包
         * Attachment: 异步channel携带的附件，通知回调的时候作为入参使用
         * CompletionHandler: 接收通知回调的业务handler
         */
        asynchronousSocketChannel.read(readBuffer, readBuffer, new ReadCompletionHandler(asynchronousSocketChannel));
    }

    @Override
    public void failed(Throwable exc, AsynTimeServerHandler attachment) {
        exc.printStackTrace();
        attachment.countDownLatch.countDown();
    }

}
