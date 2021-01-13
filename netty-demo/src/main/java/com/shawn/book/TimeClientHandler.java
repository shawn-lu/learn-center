package com.shawn.book;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.logging.Logger;

/**
 * Created by lxf on 2020/12/30.
 */
public class TimeClientHandler extends ChannelHandlerAdapter{
    private final Logger logger = Logger.getLogger(TimeClientHandler.class.getName());

    private int counter = 0;
    private byte[] req;


    public TimeClientHandler(){
        req = ("QUERY TIME ORDER"+System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        try {
            ByteBuf message = null;
            for(int i=0;i<50;i++){
//                counter++;
                message = Unpooled.buffer(req.length);
                message.writeBytes(req);
//                System.out.println(message);
                ctx.writeAndFlush(message);
            }
            System.out.println(counter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String)msg;
        System.out.println("now is " + body);

//        System.out.println(msg);
//        ByteBuf buf = (ByteBuf)msg;
//        byte[] req = new byte[buf.readableBytes()];
//        buf.readBytes(req);
//        String body = new String(req,"UTF-8");
//        System.out.println("now is " + body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warning("unexpected exception from downstream : " + cause.getMessage());
        ctx.close();
    }
}
