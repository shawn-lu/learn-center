package com.shawn.server;

import org.junit.Test;

public class TestServer {
    @Test
    public void testServer() throws Exception {
        MyRPCServer myRPCServer = new MyRPCServer();
        myRPCServer.start(5566);
    }
}
