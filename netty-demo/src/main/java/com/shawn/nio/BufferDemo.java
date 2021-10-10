package com.shawn.nio;

import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;

public class BufferDemo {
    public static void main(String[] args) throws Exception{
        String projectPath = "/Users/luxufeng/work/code/learn-center/netty-demo/";
        RandomAccessFile file = new RandomAccessFile(projectPath + "src/main/resources/data/nio-data.txt","rw");

        FileChannel channel = file.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int bytesRead = channel.read(buffer);
        while (-1!=bytesRead){
            System.out.println("read count:"+bytesRead + ",read byte:" + buffer.getChar());
            bytesRead = channel.read(buffer);
        }
    }
}
