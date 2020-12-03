package com.shawn.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyRPCClient {
    public static void main(String[] args) throws Exception {
        new MyRPCClient().start("127.0.0.1", 5566);
    }

    public void start(String host, int port) throws Exception {
        //定义⼯作线程组
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            //注意：client使⽤的是Bootstrap
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(worker)
                    .channel(NioSocketChannel.class) //注意：client使⽤的是NioSocketChannel
                    .handler(new MyClientHandler());
            //连接到远程服务
            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } finally {
            worker.shutdownGracefully();
        }
    }
}
