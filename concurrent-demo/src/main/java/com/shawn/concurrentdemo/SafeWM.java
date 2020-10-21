package com.shawn.concurrentdemo;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author luxufeng
 * @date 2019/9/5
 **/
public class SafeWM {
    class WMRange {
        final int upper = 0;
        final int lower = 0;

        WMRange(int upper, int lower) {
            // 省略构造函数实现
        }
    }

    final AtomicReference<WMRange>
            rf = new AtomicReference<>(
            new WMRange(0, 0)
    );

    // 设置库存上限
    void setUpper(int v) {
        WMRange nr;
        WMRange or = rf.get();
        do {
            // 检查参数合法性
            if (v < or.lower) {
                throw new IllegalArgumentException();
            }
            nr = new
                    WMRange(v, or.lower);
        } while (!rf.compareAndSet(or, nr));
    }
}

