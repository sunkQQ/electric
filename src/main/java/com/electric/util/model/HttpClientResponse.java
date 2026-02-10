package com.electric.util.model;

import org.apache.hc.core5.http.Header;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * http请求返回数据类
 * @author sunk
 * @date 2024/09/11
 */
@Setter
@Getter
public class HttpClientResponse {

    private String   result;

    private Header[] header;

    public HttpClientResponse(String result, Header[] header) {
        this.result = result;
        this.header = header;
    }

}
