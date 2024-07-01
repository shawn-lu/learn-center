package com.shawn.kafka.demo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {
        List<String> toSearchList = toSearch();

        List<String> dataList = new ArrayList<>();


        //BufferedReader是可以按行读取文件
        FileInputStream inputStream = new FileInputStream("/Users/luxufeng/work/githubspace/learn-center/kafka-demo/src/main/resources/testFile.log");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String str = null;
        int line = 1;
        while ((str = bufferedReader.readLine()) != null) {
            if (str.indexOf("message ") < 0) {
                continue;
            }
            str = str.substring(str.indexOf("message ") + "message ".length());
            if (line % 1000 == 0) {
                System.out.println("current " + line);
            }
            for (String data : toSearchList) {
                if (str.contains(data)) {
                    System.out.println("find " + data + "!!!" + str);
                    writeLine(str);
                }
            }
            line++;
        }

        //close
        inputStream.close();
        bufferedReader.close();

    }

    //pd_financial_order
    //id
    private static List<String> toSearch() throws Exception {
        List<String> dataList = new ArrayList<>();


        //BufferedReader是可以按行读取文件
        FileInputStream inputStream = new FileInputStream("/Users/luxufeng/work/githubspace/learn-center/kafka-demo/src/main/resources/errordata.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String str = null;
        while ((str = bufferedReader.readLine()) != null) {
            if (null == str || str.trim() == "") {
                continue;
            }
            dataList.add(str);
        }

        //close
        inputStream.close();
        bufferedReader.close();
        System.out.println(dataList.size());
        return dataList;
    }


    private static void writeLine(String str) throws Exception {
        try {
            File file = new File("/Users/luxufeng/work/githubspace/learn-center/kafka-demo/src/main/resources/result.txt");
            FileOutputStream fos = null;
            if (!file.exists()) {
                file.createNewFile();//如果文件不存在，就创建该文件
                fos = new FileOutputStream(file);//首次写入获取
            } else {
                //如果文件已存在，那么就在文件末尾追加写入
                fos = new FileOutputStream(file, true);//这里构造方法多了一个参数true,表示在文件末尾追加写入
            }

            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");//指定以UTF-8格式写入文件
            osw.write(str);
            //每写入一个Map就换一行
            osw.write("\r\n");
            //写入完成关闭流
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
