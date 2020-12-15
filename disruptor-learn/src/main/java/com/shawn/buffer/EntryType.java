package com.shawn.buffer;

/**
 * Created by lxf on 2020/12/16.
 */
public enum EntryType {
    /**
     * <code>TRANSACTIONBEGIN = 1;</code>
     */
    TRANSACTIONBEGIN(0, 1),
    /**
     * <code>ROWDATA = 2;</code>
     */
    ROWDATA(1, 2),
    /**
     * <code>TRANSACTIONEND = 3;</code>
     */
    TRANSACTIONEND(2, 3);

    private final int index;
    private final int value;

    EntryType(int index, int value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public int getValue() {
        return value;
    }
}
