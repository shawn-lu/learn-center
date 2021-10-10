package com.shawn;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

public class TempMain {

    public static void main(String[] args) throws IOException {
        File m = new File("/Users/luxufeng/work/githubspace/learn-center/data-tool/src/main/resources/member/member_sync");
        File[] files = m.listFiles();
        for (File f : files) {
            //&& f.getName().equals("t_member_mysql2es.json")
            //&& f.getName().equals("yh_market_channel_mysql2es.json")
            if (f.getName().contains("mysql2es.json")) {
                String s = output(f.getName());
                System.out.println("--------");
                System.out.println(s);
                writeFile(f.getName(), s);
            }
        }


//        FileUtils.writeStringToFile();
    }

    private static String output(String fileName) throws IOException {
        JSONObject jsonObject = getRoot("classpath:member/template.json");

        //reader
        JSONObject readerColumnKv = (JSONObject) getLeafJsonObject(jsonObject, "job/content[0]/reader/parameter");
        readerColumnKv.put("column", JSON.parseArray("[]"));
//        System.out.println(columnKv);
//        System.out.println(jsonObject);
//        System.out.println(getLeafJsonObject(jsonObject, "job/content[0]/writer/parameter/column[]"));
//        JSONObject sourceReaderObject = getRoot("classpath:member/member_sync/t_member_mysql2es.json");
        JSONObject sourceReaderObject = getRoot("classpath:member/member_sync/" + fileName);

        JSONArray readerColumn = (JSONArray) getLeafJsonObject(sourceReaderObject, "job/content[0]/reader/parameter/column[]");
        readerColumnKv.put("column", readerColumn);
//        System.out.println("!!!!!!!");
//        System.out.println(readerColumnKv);
//        System.out.println(jsonObject);
//        System.out.println("!!!!!!!");

        //writer
        JSONObject writerColumnKv = (JSONObject) getLeafJsonObject(jsonObject, "job/content[0]/writer/parameter");
        writerColumnKv.put("column", JSON.parseArray("[]"));

        JSONObject sourceWriterObject = getRoot("classpath:member/member_sync/" + fileName);
        JSONArray writerColumn = (JSONArray) getLeafJsonObject(sourceWriterObject, "job/content[0]/writer/parameter/column[]");
        writerColumnKv.put("column", writerColumn);
        String result = jsonObject.toJSONString();
        result = result.replace("\"format\": \"yyyy-MM-dd HH:mm:ss:SSS\",","\"extParamMap\": {\n" +
                "                  \"dateFormat\": \"yyyy-MM-dd HH:mm:ss:SSS\"\n" +
                "                },");
        return result;
    }

    private static void writeFile(String fileName, String contentName) throws IOException {
        String path = "/Users/luxufeng/work/githubspace/learn-center/data-tool/src/main/resources/member/member_result";
        File f = new File(path + "/" + fileName);
        if (f.exists()) {
            f.delete();
        }
        f.createNewFile();
        FileUtils.writeStringToFile(f, contentName, "UTF-8");//前面两行是读取文件
    }


    private static JSONObject getRoot(String path) throws IOException {
//        File jsonFile = ResourceUtils.getFile("classpath:member/template.json");
        File jsonFile = ResourceUtils.getFile(path);

        String template = FileUtils.readFileToString(jsonFile, "UTF-8");//前面两行是读取文件
        JSONObject jsonObject = JSON.parseObject(template);
        return jsonObject;
    }


    //不支持数组嵌套数组
    private static Object getLeafJsonObject(JSONObject root, String url) {
        String[] keys = url.split("/");
        JSONObject jsonObject = null;
        Object Object;
        for (String k : keys) {
            if (null == jsonObject) {
                jsonObject = root;
            }
            if (k.contains("[")) {
                String index = k.substring(k.indexOf("[") + 1, k.indexOf("[") + 2);
                if (null != index && "]".equals(index)) {
                    return jsonObject.getJSONArray(k.substring(0, k.indexOf("[")));
                } else {
                    jsonObject = jsonObject.getJSONArray(k.substring(0, k.indexOf("["))).getJSONObject(Integer.parseInt(index));
                }
            } else {
                jsonObject = jsonObject.getJSONObject(k);
            }
        }
        return jsonObject;
    }

    private static JSONArray getLeafJsonArray(JSONObject root, String url) {
        String[] keys = url.split("/");
        JSONObject jsonObject = null;
        String k;
        for (int i = 0; i <= keys.length - 1; i++) {
            k = keys[i];
            if (null == jsonObject) {
                jsonObject = root.getJSONObject(k);
            } else if (i == keys.length - 1) {
                return jsonObject.getJSONArray(k);
            } else {
                jsonObject = jsonObject.getJSONObject(k);
            }
        }
        return null;
    }
}
