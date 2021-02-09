package com.shawn.demo.hbase;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

/**
 * @author luxufeng
 * @date 2020-12-12
 **/
public class Test2 {
    static final String CONNECT_ADDR = "127.0.0.1:2181";

    static final int SESSION_TIMEOUT = 5000;


    public static void main(String[] args) throws InterruptedException {

        ZkClient zkClient = new ZkClient(new ZkConnection(CONNECT_ADDR), SESSION_TIMEOUT);
        zkClient.createEphemeral("/xtemp"); //创建临时节点，会话失效后删除

        zkClient.createPersistent("/super/c1", true); //创建持久化节点，true表示如果父节点不存在则创建父节点

        Thread.sleep(10000);

        zkClient.delete("/xtemp"); //删除节点

        zkClient.deleteRecursive("/super");
    }
}
