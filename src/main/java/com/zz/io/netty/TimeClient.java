package com.zz.io.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author zhangzhen
 * @create 2023/2/7 22:40
 */
public class TimeClient {

    public static void main(String[] args) {
        new TimeClient().connect("127.0.0.1", 8080);
    }

    public void connect(String host, Integer port) {
        NioEventLoopGroup group = new NioEventLoopGroup(4);
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new TimeClientInitializer());
            // 发起异步连接操作
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            // 等待客户端链路关闭
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            group.shutdownGracefully();
        }
    }

    private class TimeClientInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel channel) throws Exception {
            channel.pipeline().addLast(new TimeClientHandler());
        }

    }

}
