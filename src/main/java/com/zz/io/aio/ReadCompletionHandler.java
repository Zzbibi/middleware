package com.zz.io.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * 从channel读取数据成功之后的回调
 *
 * @Author zhangzhen
 * @create 2023/2/6 22:00
 */
public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel asynSocketChannel;

    public ReadCompletionHandler(AsynchronousSocketChannel asynSocketChannel) {
        this.asynSocketChannel = asynSocketChannel;
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] body = new byte[attachment.remaining()];
        attachment.get(body);
        String req = new String(body, StandardCharsets.UTF_8);
        System.out.println("The time server receive order:" + req);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(req) ?
                new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
        doWrite(currentTime);
    }

    private void doWrite(String currentTime) {
        byte[] bytes = currentTime.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        asynSocketChannel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                if (attachment.hasRemaining()) {
                    asynSocketChannel.write(writeBuffer, writeBuffer, this);
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                try {
                    asynSocketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            asynSocketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
