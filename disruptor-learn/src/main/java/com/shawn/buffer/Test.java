package com.shawn.buffer;

/**
 * Created by lxf on 2020/12/16.
 */
public class Test {

    public static void main(String[] args) {
        for (int i = 0; i <= 1026; i++) {
            System.out.println("seq:" + i + ",index:" + getIndex(i));
        }
    }

    private static int getIndex(long sequcnce) {
        int indexMask = 1023;
        return (int) sequcnce & indexMask;
    }
}
