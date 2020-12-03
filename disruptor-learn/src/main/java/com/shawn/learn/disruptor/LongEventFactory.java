package com.shawn.learn.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Created by lxf on 2020/11/22.
 */
public class LongEventFactory implements EventFactory<LongEvent> {

    public LongEvent newInstance() {
        return new LongEvent();
    }
}
