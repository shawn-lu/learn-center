package com.shawn.learn.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * Created by lxf on 2020/11/22.
 */
public class LongEventHandler implements EventHandler<LongEvent> {

    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("消费者:" + event.getValue());
    }
}