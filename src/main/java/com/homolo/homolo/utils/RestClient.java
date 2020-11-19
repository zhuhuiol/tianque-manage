package com.homolo.homolo.utils;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * 请求restful接口类.
 * 20200921/zh.
 */
public class RestClient {

    public RestClient (String url) {
        this.url = url;
    }
    public RestClient () {
    }

    /**
     * 请求地址.
     */
    private String url;
    /**
     * 编码.
     */
    private String charset = "UTF-8";
    /**
     * 参数.
     */
    private Map<String, String> params;
    /**
     * header参数
     */
    private Map<String, String> headerParams;

    /**
     * 超时时间,默认10秒，单位ms.
     */
    private int timeOut = 10000;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setParams(Map params) {
        this.params = params;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public void setHeaderParams(Map headerParams) {
        this.headerParams = headerParams;
    }
    // -- get post put delete

    /**
     * 请求类型.
     */
    public enum HttpMethod {
        Get, Post, Put, Delete
    }


    public String httpRequest(String url, Map<String, String> params, HttpMethod httpMethod) {
        this.url = url;
        this.params = params;
        String s = this.httpRequest(httpMethod);
        return s;
    }

    public String httpRequest(Map<String, String> params, HttpMethod httpMethod) {
        this.params = params;
        String s = this.httpRequest(httpMethod);
        return s;
    }

    public String httpRequest(HttpMethod httpMethod) {
        CloseableHttpClient httpClient = null;
        try {
            httpClient = buildSSLCloseableHttpClient();
            HttpResponse response;

            if (null == httpMethod) {
                throw new RuntimeException("Http Method should be Get, Post, Put or Delete.");
            }
            if (HttpMethod.Get == httpMethod) {
                URIBuilder uriBuilder = setUrlData();
                HttpGet httpGet = new HttpGet(uriBuilder.build());
                // 设置超时时间
                RequestConfig requestConfig =  RequestConfig.custom().setSocketTimeout(this.timeOut).setConnectTimeout(this.timeOut).build();
                httpGet.setConfig(requestConfig);
                /* 设置header */
                setHeader(httpGet);
                response = httpClient.execute(httpGet);
            } else {
                HttpEntityEnclosingRequestBase requestBase = null;

                switch (httpMethod) {
                    case Post:
                        requestBase = new HttpPost(this.url);
                        break;
                    case Put:
                        requestBase = new HttpPut(this.url);
                        break;
                    case Delete:
                        URIBuilder uriBuilder = setUrlData();
                        requestBase = new HttpDeleteWithBody(uriBuilder.build().toString());
                        break;
                }

                // 设置超时时间
                RequestConfig requestConfig =  RequestConfig.custom().setSocketTimeout(this.timeOut).setConnectTimeout(this.timeOut).build();
                requestBase.setConfig(requestConfig);

                //设置header
                setHeader(requestBase);
                // 创建参数列表
                if (this.params != null && !this.params.isEmpty()) {
                    if (this.headerParams.containsKey("Content-Type") && "application/json".equals(this.headerParams.get("Content-Type"))) {
//                        StringEntity requestEntity = new StringEntity(JSONUtils.toJSONString(this.params), ContentType.APPLICATION_JSON);
                        StringEntity requestEntity = new StringEntity(new JSONObject(this.params).toString(), ContentType.APPLICATION_JSON);
                        requestBase.setEntity(requestEntity);
                    } else {
                        List<NameValuePair> paramList = new ArrayList<>();
                        Set<Map.Entry<String, String>> entries = this.params.entrySet();
                        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, String> next = iterator.next();
                            paramList.add(new BasicNameValuePair(next.getKey(), String.valueOf(next.getValue())));
                        }
                        // 模拟表单
                        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, this.charset);
                        requestBase.setEntity(entity);
                    }
                }

                response = httpClient.execute(requestBase);
            }
            HttpEntity httpEntity = response.getEntity();
            return EntityUtils.toString(httpEntity);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static CloseableHttpClient buildSSLCloseableHttpClient() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
                public boolean isTrusted(X509Certificate[] chain,
                                         String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

    /**
     * 将参数设置在url后面.
     * @return
     */
    private URIBuilder setUrlData() {
        /*
         * 由于GET请求的参数都是拼装在URL地址后方，所以我们要构建一个URL，带参数
         */
        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder(this.url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        // 创建参数列表
        if (this.params != null && !this.params.isEmpty()) {
            List<NameValuePair> list = new LinkedList<>();
            for (String key : this.params.keySet()) {
                list.add(new BasicNameValuePair(key, String.valueOf(this.params.get(key))));
            }
            uriBuilder.setParameters(list);
        }
        return uriBuilder;
    }

    /**
     * 设置header.
     * @param base
     */
    private void setHeader(HttpRequestBase base) {
        if (this.headerParams != null && !this.headerParams.isEmpty()) {
            Set<Map.Entry<String, String>> entries = this.headerParams.entrySet();
            Iterator<Map.Entry<String, String>> iterator = entries.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                base.setHeader(next.getKey(), String.valueOf(next.getValue()));
            }
        }
    }
}

