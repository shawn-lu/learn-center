//package com.shawn.kafka.demo;
//
//public class ProducerClient {
//
//    public static void main(String[] args) throws Exception{
//        new ProducerClient().testProducer();
//    }
//
//    public void testProducer() throws Exception {
//        DmsProducer<String, String> producer = new DmsProducer<String, String>();
//        int partiton = 0;
//        try {
//            for (int i = 0; i < 10; i++) {
//                String key = null;
//                String data = "The msg is " + i;
//                // 注意填写您创建的topic名称。另外，生产消息的API有多个，具体参见Kafka官网或者下文的生产消息代码。
//                producer.produce("topic-0", partiton, key, data, new Callback() {
//                    public void onCompletion(RecordMetadata metadata,
//                                             Exception exception) {
//                        if (exception != null) {
//                            exception.printStackTrace();
//                            return;
//                        }
//                        System.out.println("produce msg completed");
//                    }
//                });
//                System.out.println("produce msg:" + data);
//            }
//        } catch (Exception e) {
//            // TODO: 异常处理
//            e.printStackTrace();
//        } finally {
//            producer.close();
//        }
//    }
//
//}
