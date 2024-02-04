package com.ljq.stock.alert.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
class HttpClientUtilTest {

    static String host = "http://127.0.0.1:8089";

    static String path = "/api/stock/source/info/realTime";

    static Map<String,String> paramMap = new HashMap<>();

    static Map<String,String> headersMap = new HashMap<>();

    static {
        paramMap.put("marketType", "2");
        paramMap.put("stockCode", "002487");
        paramMap.put("name", "张三");

        headersMap.put("Authorization", "xxxxxx");
    }


    @Test
    void getHttpClient() {
    }

    @Test
    void doGet() {
        int count = 1;
        for (int i = 0; i < count; i++) {
            doGetTask();
        }



    }


    void doGetTask() {
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = SimpleHttpClientUtil.doGet(host, path, paramMap, headersMap);
            log.info("http response status:{}", httpResponse.getStatusLine().getStatusCode());
            log.info("http response content:{}", EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("http request error",e);
        } finally {
            if (Objects.nonNull(httpResponse)) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    log.error("http response close error", e);
                }
            }
        }

    }
}