package com.electric.util;

import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.hc.client5.http.ConnectionKeepAliveStrategy;
import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.DefaultClientTlsStrategy;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HeaderElement;
import org.apache.hc.core5.http.HeaderElements;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.http.message.MessageSupport;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.apache.hc.core5.util.Args;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.electric.exception.BizException;
import com.electric.model.constant.Numbers;
import com.electric.util.enums.ContentTypeEnum;
import com.electric.util.enums.HttpRequestTimeoutEnum;
import com.electric.util.model.HttpClientResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * HttpClient5工具类
 *
 * 包含SSL忽略证书、连接池管理、超时配置、重试策略等功能
 */
@Slf4j
public class HttpClient5Util {
    protected static final String                       QUESTION_MARK               = "?";
    protected static final String                       AND_MARK                    = "&";
    protected static final String                       CHARTSET_UTF8               = "UTF-8";
    protected static final String                       HTTPS                       = "https";
    protected static final String                       HTTP                        = "http";
    protected static final String                       X_REQUESTED_WITH            = "X-Requested-With";
    protected static final String                       ACCEPT                      = "Accept";
    protected static final String                       APPLICATION_JSON            = "application/json";
    protected static final String                       XML_HTTP_REQUEST            = "XMLHttpRequest";
    protected static final String                       XML_HTTP_REQUEST2           = "application/json";
    protected static final String                       USER_AGENT_KEY              = "user-agent";
    protected static final String                       USER_AGENT_VALUE            = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36";
    protected static RequestConfig                      requestConfig;
    protected static RequestConfig                      requestConfig2;
    /**会话保持*/
    protected static ConnectionKeepAliveStrategy        connectionKeepAliveStrategy = null;
    protected static PoolingHttpClientConnectionManager connManager;
    protected static CloseableHttpClient                httpsclient;
    protected static CloseableHttpClient                httpsclient2;

    // 静态初始化块，初始化HTTP客户端配置
    static {

        connectionKeepAliveStrategy = new ConnectionKeepAliveStrategy() {
            @Override
            public TimeValue getKeepAliveDuration(HttpResponse response, HttpContext context) {
                Args.notNull(response, "HTTP response");
                final Iterator<HeaderElement> it = MessageSupport.iterate(response, HeaderElements.KEEP_ALIVE);
                while (it.hasNext()) {
                    final HeaderElement he = it.next();
                    final String param = he.getName();
                    final String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        try {
                            return TimeValue.ofSeconds(Long.parseLong(value));
                        } catch (final NumberFormatException ignore) {
                        }
                    }
                }
                return TimeValue.ofSeconds(10);
                //final HttpClientContext clientContext = HttpClientContext.cast(context);
                //final RequestConfig requestConfig = clientContext.getRequestConfigOrDefault();
                //return requestConfig.getConnectionKeepAlive();
            }
        };

        try {
            // 忽略证书验证的SSL上下文
            SSLContext sslContext = SSLContextBuilder.create().loadTrustMaterial(new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();

            // 创建SSL连接套接字工厂，不进行主机名验证
            DefaultClientTlsStrategy tlsStrategy = new DefaultClientTlsStrategy(sslContext, NoopHostnameVerifier.INSTANCE);

            // 创建连接池管理器
            connManager = PoolingHttpClientConnectionManagerBuilder.create().setTlsSocketStrategy(tlsStrategy).setMaxConnTotal(500)
                .setMaxConnPerRoute(200).setDefaultConnectionConfig(ConnectionConfig.custom().setSocketTimeout(Timeout.ofSeconds(30))
                    .setConnectTimeout(Timeout.ofSeconds(10)).setValidateAfterInactivity(TimeValue.ofSeconds(60)).build())
                .build();
        } catch (Exception e) {
            log.error("SSL context initialization failed", e);
        }

        // 创建请求配置（连接超时3秒，响应超时10秒）
        requestConfig = RequestConfig.custom().setResponseTimeout(Timeout.ofMilliseconds(10000))
            .setConnectionRequestTimeout(Timeout.ofMilliseconds(3000)).build();

        // 创建另一个请求配置（连接超时3秒，响应超时30秒）
        requestConfig2 = RequestConfig.custom().setResponseTimeout(Timeout.ofMilliseconds(30000))
            .setConnectionRequestTimeout(Timeout.ofMilliseconds(3000)).build();

        // 创建HTTP客户端实例
        httpsclient = HttpClients.custom().setConnectionManager(connManager).setKeepAliveStrategy(connectionKeepAliveStrategy)
            .setDefaultRequestConfig(requestConfig).build();
        httpsclient2 = HttpClients.custom().setConnectionManager(connManager).setKeepAliveStrategy(connectionKeepAliveStrategy)
            .setDefaultRequestConfig(requestConfig2).build();

        // 注册JVM关闭钩子
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("JVM关闭钩子执行，正在清理HTTP连接池...");
            shutdownGracefully();
        }));
    }

    /**
     * 发送GET请求
     * @param url 请求地址
     * @return 响应内容
     */
    public static String sendGet(String url) {
        return sendGet(url, new HashMap<>());
    }

    /**
     * 发送带参数的GET请求
     * @param url 请求地址
     * @param params 请求参数
     * @return 响应内容
     */
    public static String sendGet(String url, Map<String, String> params) {
        return sendGet(url, params, HttpRequestTimeoutEnum.ten);
    }

    /**
     * 发送带参数和请求头的GET请求
     * @param url 请求地址
     * @param params 请求参数
     * @param head 请求头
     * @return 响应内容
     */
    public static String sendGet(String url, Map<String, String> params, Map<String, String> head) {
        return sendGet(url, params, head, HttpRequestTimeoutEnum.ten);
    }

    /**
     * 发送带参数和超时设置的GET请求
     * @param url 请求地址
     * @param params 请求参数
     * @param timeoutEnum 超时枚举
     * @return 响应内容
     */
    public static String sendGet(String url, Map<String, String> params, HttpRequestTimeoutEnum timeoutEnum) {
        return sendGet(url, params, new HashMap<>(), timeoutEnum);
    }

    /**
     * 发送带参数、请求头和超时设置的GET请求
     * @param url 请求地址
     * @param params 请求参数
     * @param head 请求头
     * @param timeoutEnum 超时枚举
     * @return 响应内容
     */
    public static String sendGet(String url, Map<String, String> params, Map<String, String> head, HttpRequestTimeoutEnum timeoutEnum) {
        try {
            ClassicRequestBuilder builder = ClassicRequestBuilder.get(url);
            // 添加参数
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.addParameter(entry.getKey(), entry.getValue());
                }
            }
            // 添加头信息
            assembleHeader(builder, head, ContentTypeEnum.FORM_URL.getContentType());
            return getHttpClient(timeoutEnum).execute(builder.build(), response -> {
                try {
                    return EntityUtils.toString(response.getEntity());
                } finally {
                    // 确保实体被完全消费，释放连接
                    EntityUtils.consumeQuietly(response.getEntity());
                }
            });
        } catch (Exception e) {
            String ip = IpUtil.getRemoteIp(url);
            log.warn("发送get请求异常,ip:{},url:{}", ip, url, e);
            throw new BizException("发送get请求异常", ip, e);
        }
    }

    /**
     * 发送带基本认证的GET请求
     * @param url 请求地址
     * @param params 请求参数
     * @param name 用户名
     * @param password 密码
     * @return 响应内容
     */
    public static String sendNewGet(String url, Map<String, String> params, String name, String password) {
        BasicCredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(new AuthScope(null, -1), new UsernamePasswordCredentials(name, password.toCharArray()));
        try (CloseableHttpClient client = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).setDefaultRequestConfig(requestConfig)
            .build()) {
            ClassicRequestBuilder builder = ClassicRequestBuilder.get(url);
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.addParameter(entry.getKey(), entry.getValue());
                }
            }
            return client.execute(builder.build(), response -> {
                try {
                    return EntityUtils.toString(response.getEntity());
                } finally {
                    // 确保实体被完全消费，释放连接
                    EntityUtils.consumeQuietly(response.getEntity());
                }
            });
        } catch (Exception e) {
            log.error("发送请求失败", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 发送POST请求
     * @param url 请求地址
     * @param params 请求参数
     * @return 响应内容
     */
    public static String sendNewPost(String url, Map<String, String> params) {
        return sendNewPost(url, assembleParam(params, ContentTypeEnum.FORM_URL.getContentType()));
    }

    /**
     * 发送POST请求
     * @param url 请求地址
     * @param stringEntity 请求实体
     * @return 响应内容
     */
    public static String sendNewPost(String url, StringEntity stringEntity) {
        try (CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(requestConfig).build()) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(stringEntity);
            return client.execute(httpPost, response -> {
                try {
                    return EntityUtils.toString(response.getEntity());
                } finally {
                    // 确保实体被完全消费，释放连接
                    EntityUtils.consumeQuietly(response.getEntity());
                }
            });
        } catch (Exception e) {
            log.error("发送http post请求失败", e);
            throw new RuntimeException("发送http post请求失败", e);
        }
    }

    /**
     * 发送POST请求
     * @param url 请求地址
     * @return 响应内容
     */
    public static String sendPost(String url) {
        return sendPost(url, new HashMap<>(), new HashMap<>());
    }

    /**
     * 发送POST请求
     * @param url 请求地址
     * @param params 请求参数
     * @return 响应内容
     */
    public static String sendPost(String url, String params) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(params, ContentTypeEnum.FORM_URL.getContentType()));
        return basePost(url, httpPost, CHARTSET_UTF8, HttpRequestTimeoutEnum.ten, null).getResult();
    }

    /**
     * 发送POST请求
     * @param url 请求地址
     * @param params 请求参数
     * @return 响应内容
     */
    public static String sendPost(String url, Map<String, String> params) {
        return sendPost(url, params, new HashMap<>(), HttpRequestTimeoutEnum.ten);
    }

    /**
     * 发送POST请求
     * @param url 请求地址
     * @param params 请求参数
     * @param timeoutEnum 超时枚举
     * @return 响应内容
     */
    public static String sendPost(String url, Map<String, String> params, HttpRequestTimeoutEnum timeoutEnum) {
        return sendPost(url, params, new HashMap<>(), timeoutEnum);
    }

    /**
     * 发送POST请求
     * @param url 请求地址
     * @param params 请求参数
     * @param head 请求头
     * @return 响应内容
     */
    public static String sendPost(String url, Map<String, String> params, Map<String, String> head) {
        return sendPost(url, params, head, CHARTSET_UTF8, ContentTypeEnum.FORM_URL.getContentType(), HttpRequestTimeoutEnum.ten).getResult();
    }

    /**
     * 发送POST请求
     * @param url 请求地址
     * @param params 请求参数
     * @param head 请求头
     * @param timeoutEnum 超时枚举
     * @return 响应内容
     */
    public static String sendPost(String url, Map<String, String> params, Map<String, String> head, HttpRequestTimeoutEnum timeoutEnum) {
        return sendPost(url, params, head, CHARTSET_UTF8, ContentTypeEnum.FORM_URL.getContentType(), timeoutEnum).getResult();
    }

    /**
     * 发送POST请求
     * @param url 请求地址
     * @param params 请求参数
     * @param contentType 请使用枚举 ContentTypeEnum 中提供的ContentType
     * @return 响应对象
     */
    public static HttpClientResponse sendPost(String url, Map<String, String> params, ContentType contentType) {
        HttpPost httpPost = new HttpPost(url);
        assembleHeader(httpPost, new HashMap<>(), contentType);
        httpPost.setEntity(assembleParam(params, contentType));
        return basePost(url, httpPost, CHARTSET_UTF8, HttpRequestTimeoutEnum.ten, null);
    }

    /**
     * 发送POST请求
     * @param url 请求地址
     * @param params 请求参数
     * @param head 请求头
     * @param returnCharset 返回字符集
     * @return 响应内容
     */
    public static String sendPost(String url, Map<String, String> params, Map<String, String> head, String returnCharset) {
        HttpClientResponse sendPost = sendPost(url, params, head, returnCharset, ContentTypeEnum.FORM_URL.getContentType(),
            HttpRequestTimeoutEnum.ten);
        return sendPost.getResult();
    }

    /**
     * 发送POST请求
     * @param url 请求地址
     * @param params 请求参数
     * @param contentType 请使用枚举 ContentTypeEnum 中提供的ContentType
     * @param returnHeader returnHeader
     * @return 响应对象
     */
    public static HttpClientResponse sendPost(String url, Map<String, String> params, ContentType contentType, String returnHeader) {
        HttpPost httpPost = new HttpPost(url);
        assembleHeader(httpPost, new HashMap<>(), contentType);
        httpPost.setEntity(assembleParam(params, contentType));
        return basePost(url, httpPost, CHARTSET_UTF8, HttpRequestTimeoutEnum.ten, returnHeader);
    }

    /**
     * 发送POST请求
     * @param url 请求地址
     * @param params 请求参数
     * @param head 请求头
     * @param returnCharset 返回字符集
     * @param contentType 请使用枚举 ContentTypeEnum 中提供的ContentType
     * @param timeoutEnum 超时枚举
     * @return 响应对象
     */
    public static HttpClientResponse sendPost(String url, Map<String, String> params, Map<String, String> head, String returnCharset,
                                              ContentType contentType, HttpRequestTimeoutEnum timeoutEnum) {
        HttpPost httpPost = new HttpPost(url);
        assembleHeader(httpPost, head, contentType);
        httpPost.setEntity(assembleParam(params, contentType));
        return basePost(url, httpPost, returnCharset, timeoutEnum, null);
    }

    /**
     * 发送post请求， 注此方法适用于ContentType=application/x-www-form-urlencoded
     *
     * @param url
     * @param paramMap
     * @return
     *
     */
    public static String sendPost1(String url, Map<String, Object> paramMap) {
        return sendPost1(url, paramMap, null, CHARTSET_UTF8);
    }

    /**
     * 发送post请求， 注此方法适用于ContentType=application/x-www-form-urlencoded
     *
     * @param url
     * @param paramMap
     * @param headMap
     * @return
     *
     */
    public static String sendPost1(String url, Map<String, Object> paramMap, Map<String, String> headMap) {
        return sendPost1(url, paramMap, headMap, CHARTSET_UTF8);
    }

    /**
     * 发送post请求， 注此方法适用于ContentType=application/x-www-form-urlencoded
     *
     * @param url
     * @param map
     * @param headMap 请求头携带参数
     * @param returnCharset 返回数据编码格式
     * @return
     * @create  2018年6月26日 下午2:17:55 luochao
     * @history
     */
    public static String sendPost1(String url, Map<String, Object> paramMap, Map<String, String> headMap, String returnCharset) {
        Map<String, String> mapString = new HashMap<String, String>(Numbers.INT_16);
        if (paramMap != null && paramMap.size() > 0) {
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                if (entry.getValue() == null) {
                    continue;
                }
                String key = entry.getKey();
                String value = entry.getValue().toString();
                mapString.put(key, value);
            }
        }
        return sendPost(url, mapString, headMap);
    }

    /**
     * 发送带基本认证的JSON POST请求
     * @param url 请求地址
     * @param jsonParam JSON参数
     * @param name 用户名
     * @param password 密码
     * @return 响应内容
     */
    public static String sendPostJson(String url, String jsonParam, String name, String password) {
        BasicCredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(new AuthScope(null, -1), new UsernamePasswordCredentials(name, password.toCharArray()));
        try (CloseableHttpClient client = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).setDefaultRequestConfig(requestConfig)
            .build()) {
            HttpPost httpPost = new HttpPost(url);
            assembleHeader(httpPost, null, ContentType.APPLICATION_JSON);
            httpPost.setEntity(new StringEntity(jsonParam, ContentType.APPLICATION_JSON));
            return client.execute(httpPost, response -> {
                try {
                    return EntityUtils.toString(response.getEntity());
                } finally {
                    // 确保实体被完全消费，释放连接
                    EntityUtils.consumeQuietly(response.getEntity());
                }
            });
        } catch (Exception e) {
            log.error("发送sendPostJson请求失败", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 发送JSON POST请求
     * @param url 请求地址
     * @param params 请求参数
     * @param timeoutEnum 超时枚举
     * @return 响应内容
     */
    public static String sendPostJson(String url, JSONObject params, HttpRequestTimeoutEnum timeoutEnum) {
        return sendPostJson(url, params, new HashMap<>(), CHARTSET_UTF8, timeoutEnum).getResult();
    }

    /**
     * 发送JSON POST请求
     * @param url 请求地址
     * @param params 请求参数
     * @param head 请求头
     * @return 响应内容
     */
    public static String sendPostJson(String url, JSONObject params, Map<String, String> head) {
        return sendPostJson(url, params, head, CHARTSET_UTF8, HttpRequestTimeoutEnum.ten).getResult();
    }

    /**
     * 发送JSON POST请求
     * @param url 请求地址
     * @param params 请求参数
     * @param head 请求头
     * @param returnCharset 返回字符集
     * @param timeoutEnum 超时枚举
     * @return 响应对象
     */
    public static HttpClientResponse sendPostJson(String url, JSONObject params, Map<String, String> head, String returnCharset,
                                                  HttpRequestTimeoutEnum timeoutEnum) {
        return sendPostBase(url, params.toJSONString(), head, ContentType.APPLICATION_JSON, returnCharset, timeoutEnum);
    }

    /**
     * 发送JSON POST请求
     * @param url 请求地址
     * @param params 请求参数
     * @param head 请求头
     * @param timeoutEnum 超时枚举
     * @return 响应对象
     */
    public static HttpClientResponse sendPostJson(String url, JSONObject params, Map<String, String> head, HttpRequestTimeoutEnum timeoutEnum) {
        return sendPostBase(url, params.toJSONString(), head, ContentType.APPLICATION_JSON, timeoutEnum);
    }

    /**
     * 发送JSON PUT请求
     * @param url 请求地址
     * @param jsonParams JSON参数
     * @param head 请求头
     * @return 响应内容
     */
    public static String sendPutJson(String url, String jsonParams, Map<String, String> head) {
        return sendPutJson(url, jsonParams, head, CHARTSET_UTF8, ContentType.APPLICATION_JSON, HttpRequestTimeoutEnum.ten).getResult();
    }

    /**
     * 发送JSON PUT请求
     * @param url 请求地址
     * @param jsonParams JSON参数
     * @param head 请求头
     * @param returnCharset 返回字符集
     * @param contentType 请使用枚举 ContentTypeEnum 中提供的ContentType
     * @param timeoutEnum 超时枚举
     * @return 响应对象
     */
    public static HttpClientResponse sendPutJson(String url, String jsonParams, Map<String, String> head, String returnCharset,
                                                 ContentType contentType, HttpRequestTimeoutEnum timeoutEnum) {
        HttpPut httpPut = new HttpPut(url);
        assembleHeader(httpPut, head, contentType);
        httpPut.setEntity(new StringEntity(jsonParams, contentType));
        return basePost(url, httpPut, returnCharset, timeoutEnum, null);
    }

    /**
     * 发送POST请求 参数为字符串
     *
     * @param url 请求地址
     * @param jsonParams 参数(json格式)
     * @return
     * @date  2025年8月22日 下午6:17:48 luochao
     *
     */
    public static String sendPostString(String url, String jsonParams) {
        return sendPostString(url, jsonParams, null, ContentType.APPLICATION_JSON, HttpRequestTimeoutEnum.ten);
    }

    /**
     * 发送POST请求 参数字符串
     *
     * @param url 请求地址
     * @param stringParams 参数，与contentType对应
     * @param contentType 请使用枚举 ContentTypeEnum 中提供的ContentType
     * @return
     * @date  2025年8月22日 下午6:16:32 luochao
     *
     */
    public static String sendPostString(String url, String stringParams, ContentType contentType) {
        return sendPostString(url, stringParams, null, contentType, HttpRequestTimeoutEnum.ten);
    }

    /**
     * 发送字符串POST请求
     * @param url 请求地址
     * @param stringParams 字符串参数
     * @param head 请求头
     * @param contentType 请使用枚举 ContentTypeEnum 中提供的ContentType
     * @return 响应内容
     */
    public static String sendPostString(String url, String stringParams, Map<String, String> head, ContentType contentType) {
        return sendPostString(url, stringParams, head, contentType, HttpRequestTimeoutEnum.ten);
    }

    /**
     * 发送字符串POST请求,ContentTypeEnum为框架提供
     *
     * @param url
     * @param stringParams
     * @param contentTypeEnum 请使用枚举 ContentTypeEnum 中提供的ContentType
     * @return
     * @date  2025年8月27日 下午2:22:54 luochao
     *
     */
    public static String sendPostString(String url, String stringParams, ContentTypeEnum contentTypeEnum) {
        return sendPostString(url, stringParams, null, contentTypeEnum.getContentType(), HttpRequestTimeoutEnum.ten);
    }

    /**
     * 发送字符串POST请求
     * @param url 请求地址
     * @param stringParams 字符串参数
     * @param head 请求头
     * @param contentType 请使用枚举 ContentTypeEnum 中提供的ContentType
     * @param timeoutEnum 超时枚举
     * @return 响应内容
     */
    public static String sendPostString(String url, String stringParams, Map<String, String> head, ContentType contentType,
                                        HttpRequestTimeoutEnum timeoutEnum) {
        return sendPostBase(url, stringParams, head, contentType, timeoutEnum).getResult();
    }

    /**
     * 发送基础POST请求
     * @param url 请求地址
     * @param stringParams 字符串参数
     * @param head 请求头
     * @param contentType 请使用枚举 ContentTypeEnum 中提供的ContentType
     * @param timeoutEnum 超时枚举
     * @return 响应对象
     */
    private static HttpClientResponse sendPostBase(String url, String stringParams, Map<String, String> head, ContentType contentType,
                                                   HttpRequestTimeoutEnum timeoutEnum) {
        return sendPostBase(url, stringParams, head, contentType, CHARTSET_UTF8, timeoutEnum);
    }

    /**
     * 发送基础POST请求
     * @param url 请求地址
     * @param stringParams 字符串参数
     * @param head 请求头
     * @param contentType 请使用枚举 ContentTypeEnum 中提供的ContentType
     * @param returnCharset 返回字符集
     * @param timeoutEnum 超时枚举
     * @return 响应对象
     */
    private static HttpClientResponse sendPostBase(String url, String stringParams, Map<String, String> head, ContentType contentType,
                                                   String returnCharset, HttpRequestTimeoutEnum timeoutEnum) {
        HttpPost httpPost = new HttpPost(url);
        assembleHeader(httpPost, head, contentType);
        httpPost.setEntity(new StringEntity(stringParams, contentType));
        return basePost(url, httpPost, returnCharset, timeoutEnum, null);
    }

    /**
     * 发送post请求基类
     *
     * @param url url地址
     * @param request
     * @param returnCharset 返回数据编码格式
     * @param timeoutEnum 使用哪个时间的httpclient
     * @param returnHeader 是否返回输出header
     * @return
     * @date  2025年8月22日 下午5:46:58 luochao
     *
     */
    private static HttpClientResponse basePost(String url, ClassicHttpRequest request, String returnCharset, HttpRequestTimeoutEnum timeoutEnum,
                                               String returnHeader) {
        try {
            return getHttpClient(timeoutEnum).execute(request, response -> {
                try {
                    String result = EntityUtils.toString(response.getEntity(), returnCharset);
                    if (returnHeader == null) {
                        return new HttpClientResponse(result, response.getHeaders());
                    } else {
                        return new HttpClientResponse(result, response.getHeaders(returnHeader));
                    }
                } finally {
                    // 确保实体被完全消费，释放连接
                    EntityUtils.consumeQuietly(response.getEntity());
                }
            });
        } catch (Exception e) {
            String ip = IpUtil.getRemoteIp(url);
            log.warn("发送post请求异常,ip:{},url:{}", ip, url, e);
            throw new BizException("发送post请求异常", ip, e);
        }
    }

    /**
     * 组装请求参数
     * @param map 参数映射
     * @param contentType 请使用枚举 ContentTypeEnum 中提供的ContentType
     * @return 字符串实体
     */
    public static StringEntity assembleParam(Map<String, String> map, ContentType contentType) {
        if (ContentType.APPLICATION_JSON.equals(contentType)) {
            return new StringEntity(JSON.toJSONString(map), contentType);
        } else {
            List<NameValuePair> formparams = new ArrayList<>();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            return new UrlEncodedFormEntity(formparams, StandardCharsets.UTF_8);
        }
    }

    /**
     * 组装请求头
     * @param request 请求对象
     * @param head 请求头映射
     * @param contentType 请使用枚举 ContentTypeEnum 中提供的ContentType
     */
    private static void assembleHeader(ClassicHttpRequest request, Map<String, String> head, ContentType contentType) {
        if (ContentType.APPLICATION_JSON.getMimeType().equals(contentType.getMimeType())) {
            request.addHeader(HttpHeaders.CONTENT_TYPE, contentType.getMimeType());
        }
        if (head != null && !head.isEmpty()) {
            for (Map.Entry<String, String> entry : head.entrySet()) {
                request.addHeader(entry.getKey(), entry.getValue());
            }
        } else {
            request.addHeader(USER_AGENT_KEY, USER_AGENT_VALUE);
        }
    }

    /**
     * 组装请求头
     * @param builder 请求构建器
     * @param head 请求头映射
     * @param contentType 内容类型
     */
    private static void assembleHeader(ClassicRequestBuilder builder, Map<String, String> head, ContentType contentType) {
        if (ContentType.APPLICATION_JSON.getMimeType().equals(contentType.getMimeType())) {
            builder.setHeader(HttpHeaders.CONTENT_TYPE, contentType.getMimeType());
        }
        if (head != null && !head.isEmpty()) {
            for (Map.Entry<String, String> entry : head.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        } else {
            builder.setHeader(USER_AGENT_KEY, USER_AGENT_VALUE);
        }
    }

    /**
     * 获取HTTP客户端
     * @param timeoutEnum 超时枚举
     * @return HTTP客户端实例
     */
    private static CloseableHttpClient getHttpClient(HttpRequestTimeoutEnum timeoutEnum) {
        return timeoutEnum == HttpRequestTimeoutEnum.ten ? httpsclient : httpsclient2;
    }

    /**
     * 判断是否为Ajax请求
     * @param request HTTP请求
     * @return 是否为Ajax请求
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestType = request.getHeader(X_REQUESTED_WITH);
        String accept = request.getHeader(ACCEPT);
        return (XML_HTTP_REQUEST.equals(requestType) || XML_HTTP_REQUEST2.equals(requestType))
               || (accept != null && accept.contains(APPLICATION_JSON));
    }

    /**
     * 优雅关闭HTTP客户端
     */
    public static void shutdownGracefully() {
        log.info("开始关闭HTTP连接池...");

        try {
            // 1. 先关闭连接管理器（核心）
            if (connManager != null) {
                connManager.close(); // 这会等待活跃连接完成
                log.info("HTTP连接管理器已关闭");
            }
        } catch (Exception e) {
            log.warn("关闭连接管理器异常", e);
        }

        try {
            // 2. 关闭HTTP客户端实例
            if (httpsclient != null) {
                httpsclient.close();
                log.info("HTTP客户端1已关闭");
            }
        } catch (Exception e) {
            log.warn("关闭HTTP客户端1异常", e);
        }

        try {
            if (httpsclient2 != null) {
                httpsclient2.close();
                log.info("HTTP客户端2已关闭");
            }
        } catch (Exception e) {
            log.warn("关闭HTTP客户端2异常", e);
        }

        log.info("HTTP连接池关闭完成");
    }
}