package com.shawn.concurrentdemo.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by tingzeng on 2019/6/29.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreMtRequest {
    private long timestamp;
    private String sign;
    private int developerId;
    private int businessId;
    private String opBizCode;
    private String msgId;
    private int msgType;
    private  ReqMsg message;
}
