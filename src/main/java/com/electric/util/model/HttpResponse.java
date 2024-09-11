package com.electric.util.model;

import lombok.Getter;
import org.apache.http.Header;

/**
 *
 * http请求返回数据类
 * @author sunk
 * @date 2024/09/11
 */
@Getter
public class HttpResponse {

    private String   result;

    private Header[] header;

    public HttpResponse(String result, Header[] header) {
        this.result = result;
        this.header = header;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setHeader(Header[] header) {
        this.header = header;
    }
}
