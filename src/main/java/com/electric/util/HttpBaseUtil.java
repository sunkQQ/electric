package com.electric.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.electric.model.constant.Numbers;
import com.electric.exception.BizException;
import com.electric.util.enums.HttpRequestTimeoutEnum;
import com.electric.util.model.HttpResponse;

import cn.hutool.core.date.SystemClock;
import lombok.extern.slf4j.Slf4j;

/**
 * http工具类
 * @author sunk
 * @date 2024/09/11
 */
@Slf4j
public class HttpBaseUtil extends HttpBaseUtilParam {

    /**
     * 发送HttpGet请求
     *
     * @param url 请求地址
     * @return 结果
     */
    public static String sendGet(String url) {
        return sendGet(url, new HashMap<String, String>(Numbers.INT_16));
    }

    /**
     * 发 发送get请求
     *
     * @param url 请求地址
     * @param params 参数
     * @return 结果
     */
    public static String sendGet(String url, JSONObject params) {
        return sendGet(url, params, new HashMap<String, String>(Numbers.INT_16));
    }

    /**
     * 发送get请求
     *
     * @param url 请求地址
     * @param params 参数
     * @param head 请求头
     * @return 结果
     */
    public static String sendGet(String url, JSONObject params, Map<String, String> head) {
        Map<String, String> mapString = new HashMap<String, String>(Numbers.INT_16);
        for (String key : params.keySet()) {
            String value = params.getString(key);
            if (value == null) {
                continue;
            }
            mapString.put(key, value);
        }
        return sendGet(url, mapString, head);
    }

    /**
     * 发送get请求
     *
     * @param url 地址
     * @param params 请求参数
     * @return 结果
     */
    public static String sendGet(String url, Map<String, String> params) {
        return sendGet(url, params, HttpRequestTimeoutEnum.ten);
    }

    /**
     *  发送get请求
     *
     * @param url 地址
     * @param params 请求参数
     * @param head 请求头
     * @return 结果
     */
    public static String sendGet(String url, Map<String, String> params, Map<String, String> head) {
        return sendGet(url, params, head, HttpRequestTimeoutEnum.ten);
    }

    /**
     * 发送get请求
     *
     * @param url 地址
     * @param params 请求参数
     * @param timeoutEnum 超时时间
     * @return 结果
     */
    public static String sendGet(String url, Map<String, String> params, HttpRequestTimeoutEnum timeoutEnum) {
        return sendGet(url, params, new HashMap<String, String>(Numbers.INT_16), timeoutEnum);
    }

    /**
     *发送get请求
     *
     * @param url 地址
     * @param params 请求参数
     * @param head 请求头
     * @param timeoutEnum 超时时间
     * @return 结果
     */
    public static String sendGet(String url, Map<String, String> params, Map<String, String> head, HttpRequestTimeoutEnum timeoutEnum) {
        StringBuilder urlStringBuilder = new StringBuilder(Numbers.INT_256);
        urlStringBuilder.append(url);
        String paramStr = toString(assembleParam(params, CONTENTTYPE), CHARTSET_UTF8);
        if (url.contains(QUESTION_MARK) && StringUtils.isNotBlank(paramStr)) {
            urlStringBuilder.append(AND_MARK).append(paramStr);
        } else if (!url.contains(QUESTION_MARK) && StringUtils.isNotBlank(paramStr)) {
            urlStringBuilder.append(QUESTION_MARK).append(paramStr);
        }

        HttpGet httpget = new HttpGet(urlStringBuilder.toString());
        assembleHeader(httpget, head, CONTENTTYPE);
        try (CloseableHttpResponse response = getHttpClient(timeoutEnum).execute(httpget)) {
            System.out.println(response.getStatusLine().getStatusCode());
            return getString(url, response, CHARTSET_UTF8);
        } catch (Exception e) {
            String ip = IpUtil.getRemoteIp(url);
            log.warn("发送get请求异常,ip:{},url:{},  {}", ip, url, e.getMessage());
            throw new BizException("发送get请求异常", ip, e);
        }
    }

    /**
     *  发送httpGet请求,每次请求单独创建CloseableHttpClient实例
     *
     * @param url 地址
     * @param params 参数
     * @param name 账户
     * @param password 密码
     * @return 结果
     */
    public static String sendNewGet(String url, Map<String, String> params, String name, String password) {
        CloseableHttpResponse result = null;
        CloseableHttpClient createDefault = null;
        try {
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(name, password));
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(10000).setConnectionRequestTimeout(3000)
                .build();
            createDefault = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).setDefaultRequestConfig(requestConfig).build();
            /*String paramStr = toString(assembleParam(params, CONTENTTYPE), CHARTSET_UTF8);
            StringBuilder urlStringBuilder = new StringBuilder(Numbers.INT_256);
            if (url.contains(QUESTION_MARK) && StringUtils.isNotBlank(paramStr)) {
                urlStringBuilder.append(AND_MARK).append(paramStr);
            } else if (!url.contains(QUESTION_MARK) && StringUtils.isNotBlank(paramStr)) {
                urlStringBuilder.append(QUESTION_MARK).append(paramStr);
            }*/
            HttpGet get = new HttpGet(url);
            result = createDefault.execute(get);
            return EntityUtils.toString(result.getEntity());
        } catch (Exception e) {
            log.error("发送 IOException请求失败", e);
            throw new RuntimeException(e);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (IOException ignored) {
                }
            }
            if (createDefault != null) {
                try {
                    createDefault.close();
                } catch (IOException ignored) {
                }
            }
        }

    }

    /**
     * 发送httpGet请求,每次请求单独创建CloseableHttpClient实例
     *
     * @param url 地址
     * @param params 参数
     * @return 结果
     */
    public static String sendNewPost(String url, Map<String, String> params) {
        CloseableHttpClient createDefault = null;
        CloseableHttpResponse result = null;
        try {
            createDefault = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(assembleParam(params, CONTENTTYPE));
            result = createDefault.execute(httpPost);
            return EntityUtils.toString(result.getEntity());
        } catch (Exception e) {
            throw new RuntimeException("发送http post请求失败", e);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (IOException ignored) {
                }
            }
            if (createDefault != null) {
                try {
                    createDefault.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     *
     * sentPost:发送无参post请求
     *
     * @author luochao
     * @date 2018年1月29日 上午11:38:28
     * @param url 地址
     * @return 结果
     */
    public static String sendPost(String url) {
        return sendPost(url, new HashMap<String, String>(Numbers.INT_1), new HashMap<String, String>(Numbers.INT_1));
    }

    /**
     * 发送post请求
     *
     * @param url 地址
     * @param params 参数
     * @return 结果
     */
    public static String sendPost(String url, String params) {
        HttpPost httppost = new HttpPost(url);
        try {
            StringEntity stringEntity = new StringEntity(params, CONTENTTYPE);
            httppost.setEntity(stringEntity);
        } catch (Exception e) {
            throw new RuntimeException("发送post请求异常", e);
        }
        HttpResponse basePost = basePost(url, httppost, CHARTSET_UTF8, HttpRequestTimeoutEnum.ten);
        return basePost.getResult();
    }

    /**
     *
     * sentPost:发送post请求， 注此方法适用于ContentType=application/x-www-form-urlencoded
     *
     * @param url 地址
     * @param params 参数
     * @return 结果
     */
    public static String sendPost(String url, Map<String, String> params) {
        return sendPost(url, params, new HashMap<String, String>(Numbers.INT_1), HttpRequestTimeoutEnum.ten);
    }

    /**
     * 发送post请求
     *
     * @param url 地址
     * @param params 参数
     * @param timeoutEnum 超时
     * @return 结果
     */
    public static String sendPost(String url, Map<String, String> params, HttpRequestTimeoutEnum timeoutEnum) {
        return sendPost(url, params, new HashMap<String, String>(Numbers.INT_1), timeoutEnum);
    }

    /****
     *
     * sendPost:发送post请求
     *
     * @param url 地址
     * @param params 参数
     * @param head 请求头中的参数
     * @return 结果
     */
    public static String sendPost(String url, Map<String, String> params, Map<String, String> head) {
        HttpResponse sendPost = sendPost(url, params, head, CHARTSET_UTF8, CONTENTTYPE, HttpRequestTimeoutEnum.ten);
        return sendPost.getResult();
    }

    /**
     * 发送post请求
     *
     * @param url 地址
     * @param params 参数
     * @param head 请求头中的参数
     * @param timeoutEnum 超时枚举
     * @return 结果
     */
    public static String sendPost(String url, Map<String, String> params, Map<String, String> head, HttpRequestTimeoutEnum timeoutEnum) {
        HttpResponse sendPost = sendPost(url, params, head, CHARTSET_UTF8, CONTENTTYPE, timeoutEnum);
        return sendPost.getResult();
    }

    /**
     * 发送post请求
     *
     * @param url 地址
     * @param params 参数
     * @param head 请求头中的参数
     * @param returnCharset 返回数据编码格式
     * @return 结果
     */
    public static String sendPost(String url, Map<String, String> params, Map<String, String> head, String returnCharset) {
        HttpResponse sendPost = sendPost(url, params, head, returnCharset, CONTENTTYPE, HttpRequestTimeoutEnum.ten);
        return sendPost.getResult();
    }

    public static HttpResponse sendPost(String url, Map<String, String> params, ContentType contentType) {
        HttpPost httpPost = new HttpPost(url);
        assembleHeader(httpPost, new HashMap<String, String>(Numbers.INT_1), contentType);
        httpPost.setEntity(assembleParam(params, contentType));
        return basePost(url, httpPost, CHARTSET_UTF8, HttpRequestTimeoutEnum.ten);
    }

    /**
     * 发送post请求
     *
     * @param url 地址
     * @param params 参数
     * @param head 请求头
     * @param returnCharset 返回编码方式
     * @param timeoutEnum 时长
     * @return 结果
     */
    public static HttpResponse sendPost(String url, Map<String, String> params, Map<String, String> head, String returnCharset,
                                        ContentType contentType, HttpRequestTimeoutEnum timeoutEnum) {
        HttpPost httpPost = new HttpPost(url);
        assembleHeader(httpPost, head, contentType);
        httpPost.setEntity(assembleParam(params, contentType));
        return basePost(url, httpPost, returnCharset, timeoutEnum);
    }

    /**
     * 发送post请求
     *
     * @param url 地址
     * @param params 参数
     * @param head 请求头
     * @param returnCharset 返回编码方式
     * @param timeoutEnum 时长
     * @return 结果
     */
    public static HttpResponse sendPost(String url, String params, Map<String, String> head, String returnCharset, ContentType contentType,
                                        HttpRequestTimeoutEnum timeoutEnum) {
        HttpPost httpPost = new HttpPost(url);
        assembleHeader(httpPost, head, contentType);
        httpPost.setEntity(new StringEntity(params, Consts.UTF_8));
        return basePost(url, httpPost, returnCharset, timeoutEnum);
    }

    /**
     * 发送post请求
     *
     * @param url 地址
     * @param params 请求参数
     * @return 结果
     */
    public static String sendPostJson(String url, JSONObject params) {
        return sendPostJson(url, params, HttpRequestTimeoutEnum.ten);
    }

    /**
     * 发送post请求(每次请求新建CloseableHttpClient)
     *
     * @param url 地址
     * @param jsonParam json格式的请求参数
     * @param name 账号
     * @param password 密码
     * @return 结果
     */
    public static String sendPostJson(String url, String jsonParam, String name, String password) {
        CloseableHttpResponse result = null;
        CloseableHttpClient createDefault = null;
        try {
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(name, password));
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(10000).setConnectionRequestTimeout(3000)
                .build();
            createDefault = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).setDefaultRequestConfig(requestConfig).build();
            HttpPost httpPost = new HttpPost(url);
            assembleHeader(httpPost, null, ContentType.APPLICATION_JSON);
            StringEntity stringEntity = new StringEntity(jsonParam, ContentType.APPLICATION_JSON.getCharset());
            httpPost.setEntity(stringEntity);
            result = createDefault.execute(httpPost);
            return EntityUtils.toString(result.getEntity());
        } catch (Exception e) {
            log.error("发送sendPostJson请求失败", e);
            throw new RuntimeException(e);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (IOException ignored) {
                }
            }
            if (createDefault != null) {
                try {
                    createDefault.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * 发送post请求
     *
     * @param url 地址
     * @param params 请求参数
     * @param timeoutEnum 超时枚举
     * @return 结果
     */
    public static String sendPostJson(String url, JSONObject params, HttpRequestTimeoutEnum timeoutEnum) {
        HttpResponse sendPostJson = sendPostJson(url, params, new HashMap<String, String>(Numbers.INT_1), CHARTSET_UTF8, ContentType.APPLICATION_JSON,
            timeoutEnum);
        return sendPostJson.getResult();
    }

    /**
     *  发送post请求
     *
     * @param url 地址
     * @param params 参数
     * @param head 请求头
     * @return 结果
     */
    public static String sendPostJson(String url, JSONObject params, Map<String, String> head) {
        HttpResponse sendPostJson = sendPostJson(url, params, head, CHARTSET_UTF8, ContentType.APPLICATION_JSON, HttpRequestTimeoutEnum.ten);
        return sendPostJson.getResult();
    }

    public static String sendPostJson(String url, String jsonParams, Map<String, String> head) {
        HttpResponse sendPostJson = sendPostJson(url, jsonParams, head, CHARTSET_UTF8, ContentType.APPLICATION_JSON, HttpRequestTimeoutEnum.ten);
        return sendPostJson.getResult();
    }

    public static String sendPostJson(String url, String jsonParams, Map<String, String> head, HttpRequestTimeoutEnum timeoutEnum) {
        HttpResponse sendPostJson = sendPostJson(url, jsonParams, head, CHARTSET_UTF8, ContentType.APPLICATION_JSON, timeoutEnum);
        return sendPostJson.getResult();
    }

    public static HttpResponse sendPostJson(String url, String jsonParams, Map<String, String> head, String returnCharset, ContentType contentType,
                                            HttpRequestTimeoutEnum timeoutEnum) {
        HttpPost httpPost = new HttpPost(url);
        assembleHeader(httpPost, head, contentType);
        StringEntity stringEntity = new StringEntity(jsonParams, contentType.getCharset());
        httpPost.setEntity(stringEntity);
        return basePost(url, httpPost, returnCharset, timeoutEnum);
    }

    /**
     * 发送post请求
     *
     * @param url 地址
     * @param params 参数
     * @param head 请求头
     * @param returnCharset 返回编码方式
     * @param timeoutEnum 时长
     * @return 结果
     */
    public static HttpResponse sendPostJson(String url, JSONObject params, Map<String, String> head, String returnCharset, ContentType contentType,
                                            HttpRequestTimeoutEnum timeoutEnum) {
        HttpPost httpPost = new HttpPost(url);
        assembleHeader(httpPost, head, contentType);
        StringEntity stringEntity = new StringEntity(params.toJSONString(), contentType.getCharset());
        httpPost.setEntity(stringEntity);
        return basePost(url, httpPost, returnCharset, timeoutEnum);
    }

    public static String sendPutJson(String url, String jsonParams, Map<String, String> head) {
        HttpResponse sendPostJson = sendPutJson(url, jsonParams, head, CHARTSET_UTF8, ContentType.APPLICATION_JSON, HttpRequestTimeoutEnum.ten);
        return sendPostJson.getResult();
    }

    public static HttpResponse sendPutJson(String url, String jsonParams, Map<String, String> head, String returnCharset, ContentType contentType,
                                           HttpRequestTimeoutEnum timeoutEnum) {
        HttpPut httpPut = new HttpPut(url);
        assembleHeader(httpPut, head, contentType);
        StringEntity stringEntity = new StringEntity(jsonParams, contentType.getCharset());
        httpPut.setEntity(stringEntity);
        return basePost(url, httpPut, returnCharset, timeoutEnum);
    }

    /**
     * 发送post请求， 注此方法适用于ContentType=application/x-www-form-urlencoded
     *
     * @param url 地址
     * @param paramMap 参数
     * @param returnCharset 类型
     * @return 结果
     */
    public static String sendPost1(String url, Map<String, Object> paramMap, String returnCharset) {
        Map<String, String> mapString = new HashMap<String, String>(Numbers.INT_16);
        if (paramMap != null && !paramMap.isEmpty()) {
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                if (entry.getValue() == null) {
                    continue;
                }
                String key = entry.getKey();
                String value = entry.getValue().toString();
                mapString.put(key, value);
            }
        }
        return sendPost(url, mapString, new HashMap<String, String>(Numbers.INT_1));
    }

    public static String sendPost1(String url, Map<String, Object> paramMap) {
        return sendPost1(url, paramMap, CHARTSET_UTF8);
    }

    /**
     * sentPost:发送post请求， 注此方法适用于ContentType=application/x-www-form-urlencoded
     *
     * @param url 地址
     * @param paramMap 参数
     * @param headMap 请求头携带参数
     * @return 结果
     */
    public static String sendPost1(String url, Map<String, Object> paramMap, Map<String, String> headMap) {
        return sendPost1(url, paramMap, headMap, CHARTSET_UTF8);
    }

    /**
     * sentPost:发送post请求， 注此方法适用于ContentType=application/x-www-form-urlencoded
     *
     * @param url 地址
     * @param paramMap 参数
     * @param headMap 请求头携带参数
     * @param returnCharset 返回数据编码格式
     * @return 结果
     */
    public static String sendPost1(String url, Map<String, Object> paramMap, Map<String, String> headMap, String returnCharset) {
        Map<String, String> mapString = new HashMap<String, String>(Numbers.INT_16);
        if (paramMap != null && !paramMap.isEmpty()) {
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
     * 发送xml格式的post请求
     *
     * @param url 地址
     * @param xml xml参数
     * @param contentType 编码 非必填，默认 ContentType.create("text/xml", Consts.UTF_8)
     * @return 结果
     *
     */
    public static String sendPostXml(String url, String xml, ContentType contentType) {
        HttpPost httpPost = new HttpPost(url);
        Map<String, String> head = new HashMap<>();
        head.put("Content-Type", "text/xml; charset=UTF-8");
        if (contentType == null) {
            contentType = ContentType.create("text/xml", Consts.UTF_8);
        }
        assembleHeader(httpPost, head, contentType);
        httpPost.setEntity(new StringEntity(xml, contentType));
        try (CloseableHttpResponse response = getHttpClient(HttpRequestTimeoutEnum.ten).execute(httpPost)) {
            return getString(url, response, CHARTSET_UTF8);
        } catch (Exception e) {
            log.error("发送post请求异常", e);
            throw new BizException("发送post请求异常", e);
        }
    }

    /**
     * 基础post请求
     *
     * @param url 地址
     * @param requestBase HttpEntityEnclosingRequestBase
     * @param returnCharset returnCharset
     * @param timeoutEnum 时间枚举
     * @return 结果
     */
    private static HttpResponse basePost(String url, HttpEntityEnclosingRequestBase requestBase, String returnCharset,
                                         HttpRequestTimeoutEnum timeoutEnum) {
        CloseableHttpResponse response = null;
        try {
            long start = SystemClock.now();
            response = getHttpClient(timeoutEnum).execute(requestBase, new BasicHttpContext());
            long cost = SystemClock.now() - start;
            if (cost > Numbers.INT_3000) {
                log.warn("警告,请求第三方耗时过长,cost:{},url:{}", cost, url);
            }
            return new HttpResponse(getString(url, response, returnCharset), response.getAllHeaders());
        } catch (Exception e) {
            String ip = IpUtil.getRemoteIp(url);
            log.warn("发送post请求异常,ip:{},url:{}, {}", ip, url, e.getMessage());
            throw new BizException("发送post请求异常", ip, e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException ignored) {
            }
        }
    }

    /**
     *
     * assembleParam:组装参数.
     *
     * @param map 参数
     * @param contentType 参数类型
     * @return 结果
     */
    public static StringEntity assembleParam(Map<String, String> map, ContentType contentType) {
        StringEntity stringEntity = null;
        if (ContentType.APPLICATION_JSON.equals(contentType)) {
            stringEntity = new StringEntity(JSON.toJSONString(map), contentType.getCharset());
        } else {
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            stringEntity = new UrlEncodedFormEntity(formparams, contentType.getCharset());
        }
        return stringEntity;
    }

    /**
     * 参数组装
     *
     * @param jsonObject jsonObject
     * @param contentType contentType
     * @return 结果
     */
    public static StringEntity assembleParam(JSONObject jsonObject, ContentType contentType) {
        return new StringEntity(jsonObject.toJSONString(), contentType.getCharset());
    }

    /**
     *
     * assembleHeader:组装请求头.
     *
     * @author luochao
     * @date 2018年1月27日 下午1:58:03
     * @param request 请求头
     * @param contentType 请求类型
     */
    protected static void assembleHeader(HttpRequestBase request, Map<String, String> head, ContentType contentType) {
        if (ContentType.APPLICATION_JSON.getMimeType().equals(contentType.getMimeType())) {
            request.addHeader(HttpHeaders.CONTENT_TYPE, contentType.getMimeType());
        }
        if (head != null && !head.isEmpty()) {
            for (Map.Entry<String, String> next : head.entrySet()) {
                request.addHeader(next.getKey(), next.getValue());
            }
        } else {
            request.addHeader(HttpBaseUtilParam.USER_AGEENT_KEY, HttpBaseUtilParam.USER_AGEENT_VALUE);
        }
    }

    /**
     * 读取接口返回内容
     *
     * @param url 地址
     * @param response response
     * @param returnCharset returnCharset
     * @return 结果
     *
     */
    protected static String getString(String url, CloseableHttpResponse response, String returnCharset) {
        if (response == null) {
            return null;
        }
        if (log.isDebugEnabled()) {
            response.getStatusLine().getStatusCode();
        }

        return toString(response.getEntity(), returnCharset);
        //if (!(HttpStatus.SC_OK == statusCode)) {
        //    log.error("请求接口url:{},返回：{}", url, resultStr);
        //    throw new RuntimeException("请求失败");
        //}
        //return resultStr;
    }

    /**
     * httpEntity转换为字符串
     *
     * @param httpEntity httpEntity
     * @param charset 编码方式
     * @return 结果
     */
    protected static String toString(HttpEntity httpEntity, String charset) {
        try {
            return EntityUtils.toString(httpEntity, charset);
        } catch (Exception e) {
            throw new RuntimeException("读取HttpEntity异常", e);
        }
    }

    /**
     *
     * 判断是否是https请求
     *
     * @author luochao
     * @date 2018年2月11日 上午9:15:47
     * @param url 地址
     * @return 结果
     */
    public static boolean isHttps(String url) {
        return !StringUtils.isEmpty(url) && url.startsWith(HTTPS);
    }

    /**
     * 判断请求是否是ajax请求
     *
     * @param request request
     * @return 结果
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestType = request.getHeader(X_REQUESTED_WITH);
        String accept = request.getHeader(ACCEPT);
        if (StringUtils.isNotEmpty(requestType) && StringUtils.isNotEmpty(accept)
            && (XML_HTTP_REQUEST.equals(requestType) || XML_HTTP_REQUEST2.equals(requestType))) {
            return true;
        } else {
            return StringUtils.isNotEmpty(requestType) && StringUtils.isNotEmpty(accept) && accept.contains(APPLICATION_JSON);
        }
    }

    /**
     * 获取httpClipen实例
     *
     * @param timeoutEnum timeoutEnum
     * @return 结果
     */
    private static CloseableHttpClient getHttpClient(HttpRequestTimeoutEnum timeoutEnum) {
        if (HttpRequestTimeoutEnum.ten.equals(timeoutEnum)) {
            return httpsclient;
        } else {
            return httpsclient2;
        }
    }

}