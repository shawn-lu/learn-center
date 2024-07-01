package com.shawn;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class DBInfoParser {

    public static List<DBInfo> parse(String sys) {
        InputStream is;
        try {
            is = new FileInputStream(String.format("/Users/luxufeng/work/githubspace/learn-center/db-tool/src/main/resources/db-info/%s.json",sys));
            List<DBInfo> s = JsonUtils.fromJson(is, List.class, DBInfo.class);
            return s;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

}
