package com.shawn.springboot.learn.util;

import com.yonghui.common.message.bridge.model.Message2;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

@Slf4j
public class M2Utils {
    public static Message2 unserialize(byte[] bytes) {
        if (bytes != null && bytes.length != 0) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
                return (Message2) ois.readObject();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public static byte[] serialize(Object object) {
        if(object ==null){
            throw new NullPointerException("cannot serialize null object");
        }
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(object);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
