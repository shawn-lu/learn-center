package com.shawn.emqx;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


public class App {
    final static String broker = "tcp://10.195.7.79:1883";

    public static void main(String[] args) {
//        new Thread(new TopicListener("$SYS/brokers")).start();
//        new Thread(new TopicListener("$SYS/brokers/+/clients/+/connected")).start();
        new Thread(new TopicListener("$SYS/brokers/+/clients/+/connected","listen-connected-client")).start();
        new Thread(new TopicListener("$SYS/brokers/+/clients/+/disconnected","listen-disconnected-client")).start();
//        new Thread(new TopicListener("+/disconnected")).start();s
//        String pubTopic = "testtopic/1";
//        String content = "Hello World";s
//        int qos = 2;
//        String broker = "tcp://10.195.7.79:1883";
//        String clientId = "local_test";
//        MemoryPersistence persistence = new MemoryPersistence();
//
//        try {
//            MqttClient client = new MqttClient(broker, clientId, persistence);
//
//            // MQTT 连接选项
//            MqttConnectOptions connOpts = new MqttConnectOptions();
////            connOpts.setUserName("admin");
////            connOpts.setPassword("public".toCharArray());
//            // 保留会话
//            connOpts.setCleanSession(true);
//
//            // 设置回调
//            client.setCallback(new OnMessageCallback());
//
//            // 建立连接
//            System.out.println("Connecting to broker: " + broker);
//            client.connect(connOpts);
//
//            System.out.println("Connected");
//            System.out.println("Publishing message: " + content);
//
//            // 订阅
//            client.subscribe(subTopic);
//
////            // 消息发布所需参数
////            MqttMessage message = new MqttMessage(content.getBytes());
////            message.setQos(qos);
////            client.publish(pubTopic, message);
////            System.out.println("Message published");
////
////            client.disconnect();
////            System.out.println("Disconnected");
////            client.close();
////            System.exit(0);
//        } catch (MqttException me) {
//            System.out.println("reason " + me.getReasonCode());
//            System.out.println("msg " + me.getMessage());
//            System.out.println("loc " + me.getLocalizedMessage());
//            System.out.println("cause " + me.getCause());
//            System.out.println("excep " + me);
//            me.printStackTrace();
//        }
    }


    static class TopicListener implements Runnable {
        String subTopic;
        String clientId;

        TopicListener(String subTopic,String clientId) {
            this.subTopic = subTopic;
            this.clientId = clientId;
        }

        @Override
        public void run() {
            MemoryPersistence persistence = new MemoryPersistence();

            try {
                MqttClient client = new MqttClient(broker, clientId, persistence);

                // MQTT 连接选项
                MqttConnectOptions connOpts = new MqttConnectOptions();
//            connOpts.setUserName("admin");
//            connOpts.setPassword("public".toCharArray());
                // 保留会话
                connOpts.setCleanSession(true);

                // 设置回调
                client.setCallback(new OnMessageCallback());

                // 建立连接
                System.out.println("Connecting to broker: " + broker);
                client.connect(connOpts);
                System.out.println(subTopic);
                System.out.println("Connected");

                // 订阅
                client.subscribe(subTopic);
            } catch (MqttException me) {
                me.printStackTrace();
            }
        }
    }
}