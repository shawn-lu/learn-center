package com.shawn.concurrentdemo;

/**
 * @author luxufeng
 * @date 2019/9/10
 **/
public class FastJsonDemo {
    public static void main(String[] args) {
        String s = "{ \n" +
                "   \"opBizCode\":\"123453\",\n" +
                "   \"developerId\":\"100005\",\n" +
                "   \"msgType\":\"15201\",\n" +
                "   \"businessId\":\"15\",\n" +
                "   \"sign\":\"86600a4409d741bcba9a4fb31ee9e749e4212607\", \n" +
                "   \"msgId\":\"-8569638476772499581\",\n" +
                "   \"message\":\"{\\\"eventType\\\":\\\"USER_GET_CARD_EVENT\\\",\\\"eventDetail\\\": {\\\"cardNo\\\":\\\"123\\\",\\\"userOpenId\\\":\\\"123223323\\\",\\\"platformType\\\":\\\"MEI_TUAN\\\"}}\", \"timestamp\":\"1565159185\"\n" +
                "} ";
        System.out.println(s);
    }
}
