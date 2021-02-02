package com.shawn.nio.book;


/**
 * Created by lxf on 2021/1/14.
 */

/**
 * NIO客户端
 */
public class TimeClient {
    public static void main(String[] args) {
        new Thread(new TimeClientHandler("127.0.0.1",6666)).start();
    }
}
