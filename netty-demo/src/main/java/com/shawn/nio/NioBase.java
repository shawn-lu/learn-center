package com.shawn.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioBase {
    public static void main(String[] args) {
        readFile();
    }

    public static void readFile() {
        RandomAccessFile aFile = null;
        String baseDir = "/Users/luxufeng/work/githubspace/learn-center/netty-demo";
        try {
            aFile = new RandomAccessFile(baseDir + "/data/NioBase.java", "rw");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        FileChannel inChannel = aFile.getChannel();

        //create buffer with capacity of 48 bytes
        ByteBuffer buf = ByteBuffer.allocate(48);
        try {
            ////从inChannel中读取数据，写入buffer
            int bytesRead = inChannel.read(buf);
            while (bytesRead != -1) {
                //等于-1时，表示读完了

                buf.flip(); //make buffer ready for read


                while (buf.hasRemaining()) {
                    System.out.print((char) buf.get()); // read 1 byte at a time
                }

                buf.clear(); //make buffer ready for writing
                //从inChannel中读取数据，写入buffer
                bytesRead = inChannel.read(buf);

            }
            aFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
