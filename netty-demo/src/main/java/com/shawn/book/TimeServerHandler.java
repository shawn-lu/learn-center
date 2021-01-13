package com.shawn.book;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by lxf on 2020/12/27.
 */
public class TimeServerHandler extends ChannelHandlerAdapter {
    private int counter = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
        try {
            counter++;
            System.out.println("counter:" + counter);

            String body = (String)msg;
            System.out.println("the time server receive order : " + body);
            String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date().toString() : "BAD ORDER";
            System.out.println(currentTime);
            ByteBuf resp  = Unpooled.copiedBuffer(currentTime.getBytes());
            ctx.writeAndFlush(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
