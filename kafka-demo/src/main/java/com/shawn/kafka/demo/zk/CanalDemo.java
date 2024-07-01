package com.shawn.kafka.demo.zk;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.ZKUtil;


public class CanalDemo {
    public static void main(String[] args) throws Exception {
        final String dest = "suppliercenter-supplier-quote";
        new Thread(new Runnable() {
            @Override
            public void run() {
                watchCursor(dest);
//                watchAllInstance();
            }
        }).start();

//        setCursor(dest,"{\n" +
//                "  \"@type\": \"com.alibaba.otter.canal.protocol.position.LogPosition\",\n" +
//                "  \"identity\": {\n" +
//                "    \"slaveId\": -1,\n" +
//                "    \"   sourceAddress\": {\n" +
//                "   \"address\": \"10.251.77.188\",\n" +
//                "      \"port\": 3306\n" +
//                "    }\n" +
//                "  },\n" +
//                "  \"postion\": {\n" +
//                "    \"gtid\": \"\",\n" +
//                "    \"included\": false,\n" +
//                "    \"journalName\": \"binlog.001266\",\n" +
//                "    \"position\": 55260826,\n" +
//                "    \"serverId\": 3759383940,\n" +
//                "    \"timestamp\": 1640742463000\n" +
//                "  }\n" +
//                "}");

    }
//    df94f59c-8317-11eb-91ad-246e96b51f50:570209302

    private static void deleteInstance(String dest) {
        CuratorFramework client = null;
        String rootPath = "/otter/canal/destinations";
        try {
            String zkDev = "10.251.77.9:2181";
            client = ZkUtils.getClient(zkDev);
            client.start();
//            yh_srm_channelcenter_sharding_sap_dev\\.bhd_base_product_shop_sap_.*
//            yh_srm_channelcenter_sharding_sap_dev\\.bhd_base_product_shop_sap_a001

            String path = rootPath + "/" + dest;
            System.out.println("will delete path " + path);
            Thread.sleep(1500);
            ZkUtils.delete(client, path);
//            List<String> path = ZkUtils.getChildren(client, rootPath);
//            String cursorPath = rootPath + "/" + dest + "/1001/cursor";
//            String currentCursor = ZkUtils.getData(client,cursorPath);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    private static void watchAllInstance(){
        CuratorFramework client = null;
        String rootPath = "/otter/canal/destinations";
        try {
            String zkDev = "10.251.77.9:2181";
            client = ZkUtils.getClient(zkDev);
            client.start();
            while (true) {
                Thread.sleep(1000);
                System.out.println(ZkUtils.getChildren(client,rootPath).contains("yh-account-center-o2o")); ;
            }
//            List<String> path = ZkUtils.getChildren(client, rootPath);
//            String cursorPath = rootPath + "/" + dest + "/1001/cursor";
//            String currentCursor = ZkUtils.getData(client,cursorPath);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    private static void watchCursor(String dest) {
        CuratorFramework client = null;
        String rootPath = "/otter/canal/destinations";
        try {
            String zkDev = "10.251.77.9:2181";
            client = ZkUtils.getClient(zkDev);
            client.start();
            while (true) {

                String cursorPath = rootPath + "/" + dest + "/1001/cursor";
                String currentCursor = ZkUtils.getData(client, cursorPath);
                System.out.println(toPrettyFormat(currentCursor));
                Thread.sleep(1500);
            }
//            List<String> path = ZkUtils.getChildren(client, rootPath);
//            String cursorPath = rootPath + "/" + dest + "/1001/cursor";
//            String currentCursor = ZkUtils.getData(client,cursorPath);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    private static void setCursor(String dest, String v) {
        CuratorFramework client = null;
        String rootPath = "/otter/canal/destinations";
        try {
            String zkDev = "10.251.77.9:2181";
            client = ZkUtils.getClient(zkDev);
            client.start();
            String cursorPath = rootPath + "/" + dest + "/1001/cursor";

            String currentCursor = ZkUtils.getData(client, cursorPath);

//            System.out.println("set " + cursorPath + " "+currentCursor);
            ZkUtils.setData(client, cursorPath, v.getBytes());
            System.out.println("set done");
//            set /otter/canal/destinations/yhddztdsql-0/1001/cursor {"@type":"com.alibaba.otter.canal.protocol.position.LogPosition","identity":{"slaveId":-1,"sourceAddress":{"address":"10.251.76.33","port":3307}},"postion":{"gtid":"df94f59c-8317-11eb-91ad-246e96b51f50:1-570209302","included":false,"journalName":"binlog.019478","position":97016711,"serverId":2169803689,"timestamp":1636454372000}}

//            System.out.println(toPrettyFormat(currentCursor));

//            List<String> path = ZkUtils.getChildren(client, rootPath);
//            String cursorPath = rootPath + "/" + dest + "/1001/cursor";
//            String currentCursor = ZkUtils.getData(client,cursorPath);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    private static String toPrettyFormat(String json) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonObject);
    }

}

