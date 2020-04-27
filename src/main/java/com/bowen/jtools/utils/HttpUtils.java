package com.bowen.jtools.utils;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;

/**
 * Copyright (c) 2020 XiaoMi Inc. All Rights Reserved.
 * Created by: jiangbowen <jiangbowen@xiaomi.com>.
 * On 2020/4/27
 */
public class HttpUtils {

    protected static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    // get
    public static String get(String uri) {
        try {
            HttpGet get = new HttpGet(uri);
            HttpClient client = HttpClients.createDefault();
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            logger.error("failed to get:{}, {}", uri, e.getMessage() + ExceptionUtils.getStackTrace(e));
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("failed to get:{}, {}", uri, e.getMessage() + ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    // params get
    public static String getWithParameters(String uri, List<NameValuePair> parameters) {

        CloseableHttpClient client = HttpClientBuilder.create().build();
        RequestBuilder builder = RequestBuilder.get().setUri(uri);

        if (parameters != null && parameters.size() > 0) {
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, Charset.forName("UTF-8"));
            builder.setEntity(formEntity);
        }
        HttpUriRequest request = builder.build();
        try {
            HttpResponse res = client.execute(request);
            return EntityUtils.toString(res.getEntity(), Charset.forName("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("failed to getWithParameters:{}, {}", uri, e.getMessage() + ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    // params post
    public static String postWithParameters(String uri, List<NameValuePair> parameters) {

        CloseableHttpClient client = HttpClientBuilder.create().build();
        RequestBuilder builder = RequestBuilder.post().setUri(uri);

        if (parameters != null && parameters.size() > 0) {
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, Charset.forName("UTF-8"));
            builder.setEntity(formEntity);
        }
        HttpUriRequest request = builder.build();
        try {
            HttpResponse res = client.execute(request);
            return EntityUtils.toString(res.getEntity(), Charset.forName("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("failed to postWithParameters:{}, {}", uri, e.getMessage() + ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    // json post
    public static String jsonPost(String url, String jsonStr) {
        return jsonPost(url, jsonStr, null);
    }

    public static String jsonPost(String url, String jsonStr, HashMap<String, String> headers) {
        return jsonPost(url, jsonStr, headers, 3000, 20000);
    }

    public static String jsonPost(String url, String jsonStr, HashMap<String, String> headers, int timeOut, int socketTimeOut) {
        HttpPost post = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();

            // 设置超时时间
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeOut);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, socketTimeOut);

            post = new HttpPost(url);
            // 构造消息头
            post.setHeader("Content-type", "application/json; charset=utf-8");
            post.setHeader("Connection", "Close");

            if (headers != null) {
                for (String header : headers.keySet()) {
                    post.setHeader(header, headers.get(header));
                }
            }

            // 构建消息实体
            StringEntity entity = new StringEntity(jsonStr, Charset.forName("UTF-8"));
            entity.setContentEncoding("UTF-8");
            // 发送Json格式的数据请求
            entity.setContentType("application/json");
            post.setEntity(entity);
            HttpResponse response = httpClient.execute(post);
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity, "UTF-8");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("failed to jsonPost:{}, {}", url, e.getMessage() + ExceptionUtils.getStackTrace(e));
        } finally {
            if (post != null) {
                post.releaseConnection();
            }
        }
        return null;
    }

    // json put
    public static String jsonPut(String url, String json){

        String encode = "utf-8";
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPut httpput = new HttpPut(url);

        httpput.setHeader("Accept","*/*");
        httpput.setHeader("Accept-Encoding","gzip, deflate");
        httpput.setHeader("Cache-Control","no-cache");
        httpput.setHeader("Connection", "keep-alive");
        httpput.setHeader("Content-Type", "application/json;charset=UTF-8");

        StringEntity stringEntity = new StringEntity(json, encode);
        httpput.setEntity(stringEntity);
        String content = null;
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(httpput);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    // json patch
    public static String jsonPatch(String url, String jsonStr) {
        return jsonPatch(url, jsonStr, null);
    }

    public static String jsonPatch(String url, String jsonStr, HashMap<String, String> headers) {
        HttpPatch patch = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();

            // 设置超时时间
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);

            patch = new HttpPatch(url);
            // 构造消息头
            patch.setHeader("Content-type", "application/json; charset=utf-8");
            patch.setHeader("Connection", "Close");

            if (headers != null) {
                for (String header : headers.keySet()) {
                    patch.setHeader(header, headers.get(header));
                }
            }

            // 构建消息实体
            StringEntity entity = new StringEntity(jsonStr, Charset.forName("UTF-8"));
            entity.setContentEncoding("UTF-8");
            // 发送Json格式的数据请求
            entity.setContentType("application/json");
            patch.setEntity(entity);
            logger.info("HttpUtils.jsonPatch, url:{}.", url);
            HttpResponse response = httpClient.execute(patch);
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity, "UTF-8");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("failed to jsonPatch:{}, {}", url, e.getMessage() + ExceptionUtils.getStackTrace(e));
        } finally {
            if (patch != null) {
                patch.releaseConnection();
            }
        }
        return null;
    }

    // delete
    public static String delete(String uri) {
        try {
            HttpDelete delete = new HttpDelete(uri);
            HttpClient client = HttpClients.createDefault();
            HttpResponse response = client.execute(delete);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            logger.error("failed to delete:{}, {}", uri, e.getMessage() + ExceptionUtils.getStackTrace(e));
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("failed to delete:{}, {}", uri, e.getMessage() + ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

}
