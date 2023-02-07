package com.zz.io.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

/**
 * @Author zhangzhen
 * @create 2023/2/7 22:46
 */
public class TimeClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        String body = new String(bytes, StandardCharsets.UTF_8);
        System.out.println("Now is:" + body);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered------");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive------");

        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuf writeBuffer = Unpooled.buffer(req.length);
        writeBuffer.writeBytes(req);
        ctx.writeAndFlush(writeBuffer);
    }
}
