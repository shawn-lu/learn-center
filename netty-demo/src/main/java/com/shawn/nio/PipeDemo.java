package com.shawn.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class PipeDemo {
    //线程a -> pipe(sinkChannel -》 sourceChannel) -> 线程b
    public static void main(String[] args) throws IOException {
        Pipe pipe = Pipe.open();
        Pipe.SinkChannel sinkChannel = pipe.sink();
        String newData = "New String to write to file..." + System.currentTimeMillis();
        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.clear();
        buf.put(newData.getBytes());

        buf.flip();

        while(buf.hasRemaining()) {
            sinkChannel.write(buf);
        }

        Pipe.SourceChannel sourceChannel = pipe.source();
        ByteBuffer buf2 = ByteBuffer.allocate(48);

        int bytesRead = sourceChannel.read(buf2);
        buf2.flip();
        while(buf2.hasRemaining()){
            System.out.print((char)buf2.get());
        }
    }
}
