

package com.http.cn.colaless.resterr.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.SchemePortResolver;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class RestTemplateConfiguration {
    public RestTemplateConfiguration() {
    }

    @Bean
    public RestTemplateProperties restTemplateProperties() {
        return new RestTemplateProperties();
    }

    @Bean
    public RestTemplate httpRestTemplate(ClientHttpRequestFactory httpRequestFactory) {
        return new RestTemplate(httpRequestFactory);
    }

    @Bean
    public ClientHttpRequestFactory httpRequestFactory(HttpClient httpClient) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        factory.setBufferRequestBody(false);
        return factory;
    }

    @Bean
    public HttpClient httpClient(RestTemplateProperties restTemplateProperties) {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", SSLConnectionSocketFactory.getSocketFactory()).build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry, (HttpConnectionFactory)null, (SchemePortResolver)null, (DnsResolver)null, 30L, TimeUnit.MILLISECONDS);
        connectionManager.setMaxTotal(restTemplateProperties.getMaxTotal());
        connectionManager.setDefaultMaxPerRoute(restTemplateProperties.getDefaultMaxPerRoute());
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(restTemplateProperties.getSocketTimeout())
                .setConnectTimeout(restTemplateProperties.getConnectTimeout())
                .setConnectionRequestTimeout(restTemplateProperties.getConnectionRequestTimeout())
                .setContentCompressionEnabled(false)
                .build();

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(() -> {
            connectionManager.closeExpiredConnections();
            connectionManager.closeIdleConnections(5L, TimeUnit.SECONDS);
        }, 1L, 5L, TimeUnit.MINUTES);
        return HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setConnectionManager(connectionManager).setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy()).build();
    }
}
