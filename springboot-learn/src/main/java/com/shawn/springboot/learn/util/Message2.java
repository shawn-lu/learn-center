package com.shawn.springboot.learn.util;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Message2 implements Serializable {
    private static final long serialVersionUID = 11564823561324112L;

    /**
     * 数据body
     */
    private HashMap<String, Object> messageParamValues;

    /**
     * 系统参数传递
     */
    private Map<String, Object> attachment;

    public Message2(HashMap<String, Object> messageParamValues) {
        this.messageParamValues = messageParamValues;
    }

    public Message2(HashMap<String, Object> messageParamValues,
                    Map<String, Object> attachment) {
        this.messageParamValues = messageParamValues;
        this.attachment = attachment;
    }

    public Message2() {
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("messageParamValues", this.messageParamValues)
                .append("attachment", this.attachment)
                .toString();
    }

    public HashMap<String, Object> getMessageParamValues() {
        return messageParamValues;
    }

    public void setMessageParamValues(HashMap<String, Object> messageParamValues) {
        this.messageParamValues = messageParamValues;
    }

    public Map<String, Object> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }

}
