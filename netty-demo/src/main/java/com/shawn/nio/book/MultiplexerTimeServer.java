package com.shawn.nio.book;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO服务端
 */
public class MultiplexerTimeServer extends Thread{
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private boolean stopFlag;

    public MultiplexerTimeServer(int port) {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("the time server is start in port:" + port);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        new MultiplexerTimeServer(6666).start();
    }

    public void stopServer() {
        this.stopFlag = true;
    }

    @Override
    public void run() {
        while (!stopFlag) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeySet.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        if (null != key) {
                            key.cancel();
                            if (null != key.channel()) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        if(null!=selector){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            if (key.isAcceptable()) {
                System.out.println("收到 accept key");
                //整个accept操作相当于TCP三次握手
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                sc.register(selector, SelectionKey.OP_READ);
            }
            if (key.isReadable()) {
                System.out.println("收到 read key");
                //read the data
                SocketChannel sc = (SocketChannel) key.channel();
                //创建一个ByteBuffer
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                //数据写到ByteBuffer中
                int readBytes = sc.read(readBuffer);
                if (readBytes > 0) {
                    //切换到读模式
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    //从readBuffer读到byte[]数组中
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("the time server receive order : " + body);
                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
                    doWrite(sc, currentTime);
                } else if (readBytes < 0) {
                    key.cancel();
                    sc.close();
                } else {
                    //读到0字节忽略
                }
            }
        }
    }


    private void doWrite(SocketChannel channel, String response) throws IOException {
        if (null != response && response.trim().length() > 0) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip(); //写模式切换到读模式,如果漏了这行，就不能发送了,!!!容易遗忘
            channel.write(writeBuffer);
        }
    }

}
