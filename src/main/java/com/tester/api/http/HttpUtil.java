package com.tester.api.http;

import com.alibaba.fastjson.JSON;
import com.tester.api.beans.ResponseBean;
import com.tester.api.test.ExecuteTest;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

public class HttpUtil extends ExecuteTest {

    public static final int DEFAULT_CONNECT_TIMEOUT = 6000;
    public static final int DEFAULT_READ_TIMEOUT = 6000;
    public static final int DEFAULT_CONNECT_REQUEST_TIMEOUT = 6000;
    private static final int MAX_TOTAL = 64;
    private static final int MAX_PER_ROUTE = 32;
    private static RequestConfig requestConfig;
    private static PoolingHttpClientConnectionManager connectionManager;
    private static HttpClientBuilder httpBuilder;
    private static CloseableHttpClient httpClient;
    private static CloseableHttpClient httpsClient;
    private static SSLContext sslContext;

    static {
        try {
            sslContext = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            sslContext.init(null, new TrustManager[]{tm}, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static {
        requestConfig = RequestConfig.custom().setSocketTimeout(DEFAULT_READ_TIMEOUT).setConnectTimeout(DEFAULT_CONNECT_TIMEOUT).setConnectionRequestTimeout(DEFAULT_CONNECT_REQUEST_TIMEOUT).build();
        @SuppressWarnings("deprecation")
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new PlainConnectionSocketFactory())
                .register("https", new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER))
                .build();
        connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        connectionManager.setMaxTotal(MAX_TOTAL);
        connectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);
        httpBuilder = HttpClientBuilder.create();
        httpBuilder.setDefaultRequestConfig(requestConfig);
        httpBuilder.setConnectionManager(connectionManager);
        httpClient = httpBuilder.build();
//        httpsClient = httpBuilder.build();
    }

    public ResponseBean getResponse(String url, String method, HashMap<String, String> params, String token, String parameterType) throws IOException, URISyntaxException {
        httpClient = HttpClients.createDefault();
        HttpUriRequest request = sendRequst(url, method, params, token, parameterType);
        CloseableHttpResponse httpResponse = httpClient.execute(request);
        if (httpResponse != null && httpResponse.getStatusLine() != null) {
            String response = "";
            if (httpResponse.getEntity() != null) {
                response = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            }
            return new ResponseBean(response, httpResponse.getStatusLine().getStatusCode(), httpResponse.getAllHeaders());
        }
//        httpClient.close();
////        httpResponse.close();
        return new ResponseBean(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    public HttpUriRequest sendRequst(String url, String method, HashMap<String, String> params, String token, String parameterType) throws URISyntaxException, UnsupportedEncodingException {
        HttpUriRequest httpUriRequest = null;
        if ("GET".equalsIgnoreCase(method)) {
            httpUriRequest = doGet(url, params, token, parameterType);
        } else if ("POST".equalsIgnoreCase(method)) {
            httpUriRequest = doPost(url, params, token, parameterType);
        } else if ("PUT".equalsIgnoreCase(method)) {
            httpUriRequest = doPut(url, params, token, parameterType);
        } else if ("DELETE".equalsIgnoreCase(method)) {
            httpUriRequest = doDelete(url, params, token, parameterType);
        } else {
            System.out.println("未定义该请求方法：" + method);
        }
        return httpUriRequest;
    }

    public HttpUriRequest doGet(String url, HashMap<String, String> params, String token, String parameterType) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(url);
        if (params != null) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue());
            }
        }
        HttpGet get = new HttpGet(uriBuilder.build());
        get.setConfig(requestConfig);
        setToken(token, get);
        if ("Y".equals(parameterType.trim())) {
            get.setHeader("Content-Type", "application/json");
        }
        return get;
    }

    public HttpUriRequest doPost(String url, HashMap<String, String> params, String token, String parameterType) throws UnsupportedEncodingException {
        HttpPost post = new HttpPost(url);
        post.setConfig(requestConfig);
        setToken(token, post);
        setParams(params, parameterType, post);
        return post;
    }

    public HttpUriRequest doPut(String url, HashMap<String, String> params, String token, String parameterType) throws UnsupportedEncodingException {
        HttpPut put = new HttpPut(url);
        put.setConfig(requestConfig);
        setToken(token, put);
        setParams(params, parameterType, put);
        return put;
    }

    public HttpUriRequest doDelete(String url, HashMap<String, String> params, String token, String parameterType) throws UnsupportedEncodingException {
        HttpDeleteWithBody delete = new HttpDeleteWithBody(url);
        delete.setConfig(requestConfig);
        setToken(token, delete);
        setParams(params, parameterType, delete);
        return delete;
    }

    public void setParams(HashMap<String, String> params, String parameterType, HttpEntityEnclosingRequestBase httpMethod) throws UnsupportedEncodingException {
        if (MapUtils.isNotEmpty(params)) {
            if ("N".equalsIgnoreCase(parameterType.trim())) {
                List<NameValuePair> paramsList = new ArrayList<>();
                Set<String> keySet = params.keySet();
                for (String key : keySet) {
                    paramsList.add(new BasicNameValuePair(key, params.get(key)));
                }
                httpMethod.setEntity(new UrlEncodedFormEntity(paramsList, Charset.forName("utf-8")));
            } else if ("Y".equals(parameterType.trim())) {
                httpMethod.setHeader("Content-Type", "application/json;charset=UTF-8");
                String jsonString = JSON.toJSONString(params);
                httpMethod.setEntity(new StringEntity(jsonString));
            }
        }
    }

    public void setToken(String token, HttpRequestBase method) {
        if (token != null) {
            if (token.equals("access_token")) {
                method.setHeader("access_token", access_token);
            } else if (token.equals("user_token")) {
                method.setHeader("user_token", user_token);
            }
        }
    }
}
