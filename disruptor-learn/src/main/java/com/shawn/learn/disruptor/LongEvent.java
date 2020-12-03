package com.shawn.learn.disruptor;

/**
 * Created by lxf on 2020/11/22.
 */
//定义事件event  通过Disruptor 进行交换的数据类型。
public class LongEvent {

    private Long value;

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
