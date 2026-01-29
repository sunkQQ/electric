package com.electric.util;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;

import org.apache.http.Consts;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.Args;

import com.electric.model.constant.Numbers;
import com.electric.util.model.ProxyAddressInfo;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * @author sunk
 * @date 2024/09/11
 */
public abstract class HttpBaseUtilParam {

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
    protected static final String                       USER_AGEENT_KEY             = "user-agent";
    protected static final String                       USER_AGEENT_VALUE           = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36";
    protected static final ContentType                  CONTENTTYPE                 = ContentType
        .create(ContentType.APPLICATION_FORM_URLENCODED.getMimeType(), Consts.UTF_8);

    /**存放需要切换代理的请求信息   key:域名 例：aa.baidu.com */
    public static Cache<String, ProxyAddressInfo>       cache                       = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS)
        .maximumSize(100).build();
    /**会话保持*/
    protected static ConnectionKeepAliveStrategy        connectionKeepAliveStrategy = null;
    /**10s超时*/
    protected static RequestConfig                      requestConfig               = null;
    /**30s超时*/
    protected static RequestConfig                      requestConfig2              = null;
    /**连接*/
    protected static PoolingHttpClientConnectionManager connManager                 = null;
    /**重试*/
    protected static HttpRequestRetryHandler            handler                     = null;
    protected static CloseableHttpClient                httpsclient                 = null;
    protected static CloseableHttpClient                httpsclient2                = null;

    static {
        connectionKeepAliveStrategy = new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                Args.notNull(response, "HTTP response");
                final HeaderElementIterator it = new BasicHeaderElementIterator(
                    response.headerIterator(org.apache.http.protocol.HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    final HeaderElement he = it.nextElement();
                    final String param = he.getName();
                    final String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        try {
                            return Long.parseLong(value) * 1000;
                        } catch (final NumberFormatException ignore) {
                        }
                    }
                }
                return 10000;//10s
            }
        };

        try {
            // 忽略证书
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create().register(HTTPS, factory)
                .register(HTTP, PlainConnectionSocketFactory.getSocketFactory()).build();
            //设置连接池大小
            connManager = new PoolingHttpClientConnectionManager(registry);
            connManager.setMaxTotal(Numbers.INT_200);
            connManager.setDefaultMaxPerRoute(Numbers.INT_20);
        } catch (Exception e) {
        }

        //连接3s,响应10s,从连接池获取连接3s
        requestConfig = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(10000).setConnectionRequestTimeout(3000).build();
        //连接3s,响应30s,从连接池获取连接3s
        requestConfig2 = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(30000).setConnectionRequestTimeout(3000).build();

        //重试次数缩减到1次,避免大量重试造成出去网络拥堵
        handler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException arg0, int retryTimes, HttpContext arg2) {
                if (retryTimes > Numbers.INT_1) {
                    return false;
                }
                if (arg0 instanceof UnknownHostException || arg0 instanceof ConnectException || arg0 instanceof SSLException) {
                    return true;
                }
                return false;
            }
        };

        httpsclient = HttpClients.custom().setDefaultRequestConfig(requestConfig).setConnectionManager(connManager).setRetryHandler(handler)
            .setKeepAliveStrategy(connectionKeepAliveStrategy).build();

        httpsclient2 = HttpClients.custom().setDefaultRequestConfig(requestConfig2).setConnectionManager(connManager).setRetryHandler(handler)
            .setKeepAliveStrategy(connectionKeepAliveStrategy).build();
    }

}