package com.shawn.buffer;


import lombok.Getter;
import lombok.Setter;

/**
 * Created by lxf on 2020/12/16.
 */
@Setter
@Getter
public class DataEntry {
    private String message;
    private EntryType entryType;
}
