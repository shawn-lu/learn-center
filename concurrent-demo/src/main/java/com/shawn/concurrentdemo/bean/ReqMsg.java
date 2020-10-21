package com.shawn.concurrentdemo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by tingzeng on 2019/9/9.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqMsg {
    private String eventType;
    private EventDetail eventDetail;
}
