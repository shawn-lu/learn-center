
package com.http.cn.colaless.resterr.config;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestTemplateProperties {
    private int maxTotal = 200;
    private int defaultMaxPerRoute = 100;
    private int socketTimeout = 1000000;
    private int connectTimeout = 5000;
    private int connectionRequestTimeout = 1000;

}
