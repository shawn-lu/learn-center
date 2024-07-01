package com.shawn.kafka.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.shawn.kafka.demo.middle.DruidConfigDetailVo;
import com.shawn.kafka.demo.middle.EsHighRestConfigDetailVo;
import com.shawn.kafka.demo.middle.JedisConfigDetailVo;
import com.shawn.kafka.demo.middle.SdkConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


public class MiddlewareClient {

    public static void main(String[] args) {
//        String s = "jdbc:mysql://thirdparty01.master.vip.mysql.mw.yonghui.cn:3306/order_fulfillment_center?autoReconnect=true&zeroDateTimeBehavior=convertToN";
//        System.out.println(s.indexOf("/",15));
//        System.out.println(s.indexOf("//") + 2);
//        System.out.println(s.length());
//        System.out.println(s.substring(s.indexOf("//") + 2,s.indexOf("/",15)));
//        if(true)return;
        try {
            String url = "https://mmp-tke45.yonghuivip.com/app/list";
            String appStr = doGet(url);

            Response<List<App>> apps = JSON.parseObject(appStr, new TypeReference<Response<List<App>>>() {
            });

            List<App> afterOrder = apps.getData();
            afterOrder.sort(Collections.reverseOrder());

            for (App app : afterOrder) {
                String appName = app.getAppName();
                if(appName.contains("abc")){
                    continue;
                }

                String urlString = "https://mmp-tke45.yonghuivip.com/app/getConfig?applicationId=" + app.getId();

                String json = doGet(urlString);

//            SdkConfig sdkConfig = JSON.parseObject(json, Map.class);
                Response<SdkConfig> m = JSON.parseObject(json, new TypeReference<Response<SdkConfig>>() {
                });

                if (m == null || StringUtils.isEmpty(m.getData())) {
                    return;
                }
                SdkConfig sdkConfig = m.getData();
                if (CollectionUtils.isEmpty(sdkConfig.getDruidConfigs()) &&
                        CollectionUtils.isEmpty(sdkConfig.getEsHighRestConfigs())
                        && CollectionUtils.isEmpty(sdkConfig.getJedisConfigs())) {
                    continue;
                }
                Result r = new Result();
                r.setApplicationName(appName);
                r.addMysql(sdkConfig.getDruidConfigs())
                        .addEs(sdkConfig.getEsHighRestConfigs())
                        .addRedis(sdkConfig.getJedisConfigs());
                System.out.println(r);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String urlToAddress(String s) {
        return s.substring(s.indexOf("//") + 2, s.indexOf("/", 15));
    }

    @Setter
    @Getter
    static class App implements Comparable<App> {
        String id;
        String appName;

        @Override
        public int compareTo(App o) {
            return this.getId().compareTo(o.getId());
        }
    }

    @Setter
    @Getter
    static class Result {
        String applicationName;
        List<String> redis = new ArrayList<>(3);
        List<String> es = new ArrayList<>(2);
        List<String> mysql = new ArrayList<>(5);


        Result addRedis(List<JedisConfigDetailVo> jedisConfigDetailVoList) {
            Optional.ofNullable(jedisConfigDetailVoList).orElse(new ArrayList<>()).stream().forEach(j -> {
                if (j.getAddress().contains(",") && j.getAddress().split(",")[0].equalsIgnoreCase(j.getAddress().split(",")[1])) {
                    redis.add(j.getAddress().split(",")[0]);
                } else if (j.getAddress().contains(",")) {
                    redis.add("\"" + j.getAddress() + "\"");
                } else {
                    redis.add(j.getAddress());
                }
            });
            fillEmptyStr(redis, 3);
            return this;
        }

        Result addMysql(List<DruidConfigDetailVo> druidConfigDetailVoList) {
            Optional.ofNullable(druidConfigDetailVoList).orElse(new ArrayList<>()).stream().forEach(j -> {
                mysql.add(urlToAddress(j.getUrl()));
            });
            fillEmptyStr(mysql, 5);
            return this;
        }


        Result addEs(List<EsHighRestConfigDetailVo> esHighRestConfigDetailVoList) {
            Optional.ofNullable(esHighRestConfigDetailVoList).orElse(new ArrayList<>()).stream().forEach(j -> {
                if (j.getAddress().contains(",")) {
                    es.add("\"" + j.getAddress() + "\"");
                } else {
                    es.add(j.getAddress());
                }
            });
            fillEmptyStr(es, 2);
            return this;
        }

        @Override
        public String toString() {
            StringJoiner sj = new StringJoiner(",");
            sj = sj.add(applicationName).add("海星");
            for (int i = 0; i <= 2; i++) {
                sj.add(null == redis.get(i) ? "" : redis.get(i));
            }
            for (int i = 0; i <= 1; i++) {
                sj.add(null == es.get(i) ? "" : es.get(i));
            }
            for (int i = 0; i <= 4; i++) {
                sj.add(null == mysql.get(i) ? "" : mysql.get(i));
            }
            return sj.toString();
        }
    }

    static void fillEmptyStr(List<String> list, int size) {
        if (null != list && list.size() < size) {
            for (int i = list.size(); i <= size - 1; i++) {
//                if (null == list.get(i)) {
                list.add(i, "");
//                }
            }
        }

    }

    @Setter
    @Getter
    static class Response<T> {
        Integer code;
        T data;
        String message;
    }


    public static String doGet(String httpurl) throws Exception {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;// 返回结果字符串
        try {
            // 创建远程url连接对象
            URL url = new URL(httpurl);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接方式：get
            connection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(60000);
            // 发送请求
            connection.connect();
            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                // 封装输入流is，并指定字符集
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                // 存放数据
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            connection.disconnect();// 关闭远程连接
        }
        return result;
    }
}
