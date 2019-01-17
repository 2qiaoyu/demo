package com.joham.util;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * http请求封装
 *
 * @author joham
 */
public class HttpClientUtil {

    public static final String UTF8 = "UTF-8";

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * get 方法
     *
     * @param url        请求url
     * @param headersMap 头部信息封装
     * @return 接口返回
     */
    public static String get(String url, Map<String, String> headersMap) {
        String strResult = "";
        CloseableHttpClient httpClient = null;
        try {
            //设置连接超时时间
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(2 * 1000)
                    .setConnectionRequestTimeout(2000)
                    .setSocketTimeout(25 * 1000)
                    .build();

            httpClient = HttpClientBuilder.create()
                    .setConnectionManager(poolingHttpClientConnectionManager())
                    .evictExpiredConnections()
                    .setDefaultRequestConfig(requestConfig)
                    .setRetryHandler(new DefaultHttpRequestRetryHandler(3, true))
                    .build();
            HttpGet request = new HttpGet(url);
            if (headersMap != null) {
                for (Map.Entry<String, String> header : headersMap.entrySet()
                        ) {
                    request.setHeader(header.getKey(), header.getValue());
                }
            }
            request.setConfig(requestConfig);
            HttpResponse response = httpClient.execute(request);
            //请求发送成功，并得到响应
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                strResult = EntityUtils.toString(response.getEntity(), UTF8);
                url = URLDecoder.decode(url, UTF8);
                LOGGER.info("{}请求成功{}", url, strResult);
            } else {
                LOGGER.info("{}请求失败,状态码{}错误信息{}", url, response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(), UTF8));
            }
        } catch (Exception e) {
            LOGGER.info("{}请求失败,异常信息{}", url, e.getMessage());
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                // ignore
            }
        }
        return strResult;
    }

    /**
     * post方法
     *
     * @param url        请求url
     * @param headersMap 头部信息封装
     * @param paramsMap  参数封装
     * @return 接口返回
     */
    public static String post(String url, Map<String, String> headersMap, Map<String, String> paramsMap) {
        String strResult = "";
        CloseableHttpClient httpClient = null;
        try {
            //设置连接超时时间
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(2 * 1000)
                    .setConnectionRequestTimeout(1000)
                    .setSocketTimeout(25 * 1000)
                    .build();

            httpClient = HttpClientBuilder.create()
                    .setConnectionManager(poolingHttpClientConnectionManager())
                    .evictExpiredConnections()
                    .setDefaultRequestConfig(requestConfig)
                    .build();

            HttpPost request = new HttpPost(url);
            if (paramsMap != null) {
                // 用于存放请求参数
                List<NameValuePair> formparams = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
                    formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, UTF8);
                request.setEntity(entity);
            }
            if (headersMap != null) {
                for (Map.Entry<String, String> header : headersMap.entrySet()
                        ) {
                    request.setHeader(header.getKey(), header.getValue());
                }
            }
            request.setConfig(requestConfig);
            HttpResponse response = httpClient.execute(request);
            //请求发送成功，并得到响应
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                strResult = EntityUtils.toString(response.getEntity(), UTF8);
                LOGGER.info("{}请求成功{}", url, strResult);
            } else {
                LOGGER.info("{}请求失败,状态码{}错误信息{}", url, response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(), UTF8));
            }
        } catch (Exception e) {
            LOGGER.info("{}请求失败,异常信息{}", url, e.getMessage());
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                // ignore
            }
        }
        return strResult;
    }

    /**
     * post请求，参数为json
     *
     * @param url        请求地址
     * @param headersMap 头部信息
     * @param jsonParam  参数
     * @return 返回
     */
    public static String jsonPost(String url, Map<String, String> headersMap, String jsonParam) {
        String strResult = "";
        CloseableHttpClient httpClient = null;
        try {
            //设置连接超时时间
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(2 * 1000)
                    .setConnectionRequestTimeout(1000)
                    .setSocketTimeout(25 * 1000)
                    .build();

            httpClient = HttpClientBuilder.create()
                    .setConnectionManager(poolingHttpClientConnectionManager())
                    .evictExpiredConnections()
                    .setDefaultRequestConfig(requestConfig)
                    .build();
            HttpPost request = new HttpPost(url);
            if (jsonParam != null) {
                LOGGER.info("postJson参数:{}", jsonParam);
                //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam, UTF8);
                entity.setContentEncoding(UTF8);
                entity.setContentType("application/json");
                request.setEntity(entity);
            }
            if (headersMap != null) {
                for (Map.Entry<String, String> header : headersMap.entrySet()
                        ) {
                    request.setHeader(header.getKey(), header.getValue());
                }
            }
            request.setConfig(requestConfig);
            HttpResponse response = httpClient.execute(request);
            //请求发送成功，并得到响应
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                strResult = EntityUtils.toString(response.getEntity(), UTF8);
                LOGGER.info("{}请求成功{}", url, strResult);
            } else {
                LOGGER.info("{}请求失败,状态码{}错误信息{}", url, response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(), UTF8));
            }
        } catch (Exception e) {
            LOGGER.info("{}请求失败,异常信息{}", url, e.getMessage());
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                // ignore
            }
        }
        return strResult;
    }

    /**
     * 设置连接池参数
     */
    public static PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setMaxTotal(200);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(poolingHttpClientConnectionManager.getMaxTotal());
        poolingHttpClientConnectionManager.setValidateAfterInactivity(500);
        return poolingHttpClientConnectionManager;
    }
}
