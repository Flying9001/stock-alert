package com.ljq.stock.alert.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Description: 网络请求工具类--Powered by Apache HttpClient
 * @Author: junqiang.lu
 * @Date: 2019/5/16
 */
@Slf4j
public class SimpleHttpClientUtil {


    /**
     * 连接池最大连接数
     */
    private static final int CONNECT_MAX_TOTAL = 100;

    /**
     * 连接最大并发数
     */
    private static final int CONNECT_MAX_PER_ROUTE = 20;

    /**
     * 连接超时时间,单位:毫秒
     */
    private static final int CONNECT_TIMEOUT = 5000;

    /**
     * 发起请求超时时间,单位:毫秒
     */
    private static final int CONNECT_REQUEST_TIMEOUT = 5000;

    /**
     * 读取超时时间,单位:毫秒
     */
    private static final int SOCKET_TIMEOUT = 10000;

    private static volatile CloseableHttpClient httpClient;

    private SimpleHttpClientUtil(){
    }


    /**
     * GET 方式请求
     * 参数通过 url 拼接
     *
     * @param host 请求地址
     * @param path 接口路径
     * @param paramsMap 请求参数
     * @param headersMap 请求头参数
     * @return
     * @throws IOException
     */
    public static CloseableHttpResponse doGet(String host, String path, Map<String, String> paramsMap,
                                              Map<String, String> headersMap) throws IOException {
        HttpGet httpGet = new HttpGet(getRequestUri(host, path, paramsMap));
        httpGet.setHeader(HTTP.CONTENT_TYPE, ContentType.create(ContentType.APPLICATION_FORM_URLENCODED
                .getMimeType(), Consts.UTF_8).toString());
        if (Objects.nonNull(headersMap) && !headersMap.isEmpty()) {
            headersMap.forEach(httpGet::addHeader);
        }
        return getHttpClient().execute(httpGet);
    }

    /**
     * POST 方式请求
     * 参数通过 url 拼接
     *
     * @param host 请求地址
     * @param path 接口路径
     * @param paramsMap 请求参数
     * @param headersMap 请求头参数
     * @return
     * @throws IOException
     */
    public static CloseableHttpResponse doPost(String host, String path, Map<String, String> paramsMap,
                                      Map<String, String> headersMap) throws IOException {
        HttpPost httpPost = new HttpPost(getRequestUri(host, path, paramsMap));
        httpPost.setHeader(HTTP.CONTENT_TYPE, ContentType.create(ContentType.APPLICATION_FORM_URLENCODED
                .getMimeType(), Consts.UTF_8).toString());
        if (Objects.nonNull(headersMap) && !headersMap.isEmpty()) {
            headersMap.forEach(httpPost::addHeader);
        }
        return getHttpClient().execute(httpPost);
    }

    /**
     * POST 方式请求
     * 参数通过 Body 传送,JSON 格式
     *
     * @param host 请求地址
     * @param path 接口路径
     * @param jsonParams 请求参数(json 字符串)
     * @param headersMap 请求头参数
     * @return
     */
    public static CloseableHttpResponse doPost(String host, String path, String jsonParams, Map<String, String> headersMap)
            throws IOException {
        HttpPost httpPost = new HttpPost(getRequestUri(host,path, null));
        StringEntity stringentity = new StringEntity(jsonParams, ContentType.APPLICATION_JSON);
        httpPost.setEntity(stringentity);
        httpPost.addHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
        if (Objects.nonNull(headersMap) && !headersMap.isEmpty()) {
            headersMap.forEach(httpPost::addHeader);
        }
        return getHttpClient().execute(httpPost);
    }

    /**
     * POST 方式请求
     * (允许一个字段多个值)
     *
     * @param host 请求地址
     * @param path 接口路径
     * @param nameValuePairList 参数列表
     * @param headersMap 请求头参数
     * @return
     */
    public static CloseableHttpResponse doPost(String host, String path, List<NameValuePair> nameValuePairList,
                                      Map<String, String> headersMap) throws IOException {
        HttpPost httpPost = new HttpPost(getRequestUri(host, path, null));
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList,Consts.UTF_8));
        httpPost.setHeader(HTTP.CONTENT_TYPE, ContentType.create(ContentType.APPLICATION_FORM_URLENCODED.getMimeType(),
                Consts.UTF_8).toString());
        if (Objects.nonNull(headersMap) && !headersMap.isEmpty()) {
            headersMap.forEach(httpPost::addHeader);
        }
        return getHttpClient().execute(httpPost);
    }

    /**
     * POST 方式请求
     * 文件上传
     *
     * @param host 请求地址
     * @param path 接口路径
     * @param paramsMap 请求参数
     * @param fileInputStream 待上传文件流
     * @param name 文件对应字段名
     * @param fileOriginalName 原始文件名
     * @param headersMap 请求头参数
     * @return
     */
    public static CloseableHttpResponse doPost(String host, String path, Map<String, String> paramsMap,
                                      InputStream fileInputStream, String name, String fileOriginalName,
                                      Map<String, String> headersMap) throws IOException {
        HttpPost httpPost = new HttpPost(getRequestUri(host, path, paramsMap));
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        // 解决中文文件名乱码问题
        entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        entityBuilder.setCharset(Consts.UTF_8);
        ContentType contentType = ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), Consts.UTF_8);
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            entityBuilder.addTextBody(entry.getKey(), entry.getValue(), contentType);
        }
        if (fileInputStream != null && name != null && fileOriginalName != null) {
            entityBuilder.addBinaryBody(name, fileInputStream, ContentType.DEFAULT_BINARY, fileOriginalName);
        }
        httpPost.setEntity(entityBuilder.build());
        if (Objects.nonNull(headersMap) && !headersMap.isEmpty()) {
            headersMap.forEach(httpPost::addHeader);
        }
        return getHttpClient().execute(httpPost);
    }

    /**
     * 初始化 httpClient
     * @return
     */
    private static CloseableHttpClient getHttpClient() {
        if (Objects.isNull(httpClient)) {
            synchronized (SimpleHttpClientUtil.class) {
                if (Objects.isNull(httpClient)) {
                    PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
                    connectionManager.setMaxTotal(CONNECT_MAX_TOTAL);
                    connectionManager.setDefaultMaxPerRoute(CONNECT_MAX_PER_ROUTE);

                    RequestConfig requestConfig = RequestConfig.custom()
                            .setConnectTimeout(CONNECT_TIMEOUT)
                            .setConnectionRequestTimeout(CONNECT_REQUEST_TIMEOUT)
                            .setSocketTimeout(SOCKET_TIMEOUT)
                            .build();
                    httpClient = HttpClientBuilder.create()
                            .setConnectionManager(connectionManager)
                            .setDefaultRequestConfig(requestConfig)
                            .build();
                    log.info("new HttpClient,{}", httpClient);
                }
            }
        }
        return httpClient;
    }

    /**
     * 获取请求 URI
     *
     * @param host
     * @param path
     * @param paramsMap
     * @return
     */
    private static URI getRequestUri(String host, String path, Map<String, String> paramsMap) {
        try {
            String uri = host;
            if (Objects.nonNull(path) && path.length() > 0) {
                uri += path;
            }
            URIBuilder uriBuilder = new URIBuilder(uri);
            if (Objects.nonNull(paramsMap) && !paramsMap.isEmpty()) {
                paramsMap.forEach(uriBuilder::addParameter);
            }
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            log.error("get request uri error", e);
        }
        return null;
    }




}
