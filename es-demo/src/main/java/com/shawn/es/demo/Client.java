package com.shawn.es.demo;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.rest.RestStatus;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

public class Client {
    public static void main(String[] args) throws Exception{
        System.out.println(DateTime.now().toString("yyyy-MM-dd'T'HH:mm:ss:SSS'Z'"));
//        date.toString(formatter);
//        new Client().test();
    }


    private void test() throws Exception{

        RestHighLevelClient restHighLevelClient = createClient();

//        UpdateByQueryRequest request =
//                new UpdateByQueryRequest("yh_member");
//        request.setQuery(QueryBuilders.termQuery("id",8));
//        request.setScript()
//        restHighLevelClient.updateByQuery()
        String indices = "wctest";

        BulkRequest request = new BulkRequest();
        Map<String, Object> docMap = new HashMap<>();
        docMap.put("a", "xxx");
        docMap.put("b", "www");

        UpdateRequest updateRequest = new UpdateRequest(indices, "3")
                .doc(docMap)
                .docAsUpsert(true);
        request.add(updateRequest);



//
//        IndexRequest indexRequest = new IndexRequest()
//                .opType(DocWriteRequest.OpType.CREATE)
//                .id("1")
//                .index(indices)
//                .source(docMap, XContentType.JSON);
//        request.add(indexRequest);

        BulkResponse response = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        System.out.println(response.hasFailures());
        System.out.println(response.getItems()[0].status());


    }


    private RestHighLevelClient createClient() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "ak7NEDt41fu43pY6U0J6y1k9"));
        String address = "es7101-dev.mid.io:9200";
        HttpHost[] httpHosts = convertAddressToIpAndPort(address);
        RestClientBuilder builder = RestClient.builder(httpHosts).setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(10000); // 10秒
            requestConfigBuilder.setSocketTimeout(60000); // 60s
            requestConfigBuilder.setConnectionRequestTimeout(60000); //60s
            return requestConfigBuilder;
        }).setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
        );
        return new RestHighLevelClient(builder);
    }


    private HttpHost[] convertAddressToIpAndPort(String address) {
        if (StringUtils.isEmpty(address)) {
//            fail("address cannot empty");
        }
        String[] ipAndPortArray = address.split(",");
        List<HttpHost> httpHosts = new ArrayList<>();
        for (String ipAndPortStr : ipAndPortArray) {
            if (StringUtils.isEmpty(ipAndPortStr)) {
//                fail("ipAndPortStr cannot empty");
            }
            String[] ipAndPort = ipAndPortStr.split(":");
            httpHosts.add(new HttpHost(ipAndPort[0], Integer.parseInt(StringUtils.isEmpty(ipAndPort[1]) ? "80" : ipAndPort[1]), "http"));
        }
        return httpHosts.stream().toArray(HttpHost[]::new);
    }
}
