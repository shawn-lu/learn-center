package com.shawn.member;

import lombok.Builder;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;

import java.io.FileWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Utils {
    public static void printYaml(Map<String, Object> m) {
        DumperOptions options = new DumperOptions();
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);
        String s = yaml.dumpAs(m, Tag.MAP, null);
        System.out.println(s);
    }

    public static void writeToFile(Map<String, Object> m, String path, String fileName) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(path + fileName);
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Yaml yaml = new Yaml(options);
            String s = yaml.dumpAs(m, Tag.MAP, null);
            System.out.println(s);
            fw.write(s);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fw) {
                try {
                    fw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }


    @Builder
    public static class MapExt {
        Map<String, Object> value;

        MapExt put(String k, Object v) {
            if (null == value) {
                value = new LinkedHashMap<>();
            }
            value.put(k, v);
            return this;
        }

        public Map<String, Object> getValue() {
            return value;
        }
    }

    @Builder
    public static class ListExt {
        List<Object> value;

        ListExt add(Object v) {
            if (null == value) {
                value = new ArrayList<>();
            }
            value.add(v);
            return this;
        }

        public List<Object> getValue() {
            return value;
        }
    }


}
