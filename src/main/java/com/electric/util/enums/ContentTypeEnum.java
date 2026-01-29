package com.electric.util.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.hc.core5.http.ContentType;
import org.apache.http.Consts;

import com.electric.model.constant.Numbers;

/**
 *    文本内容枚举
 * @author sunk
 * @date 2025年4月30日  下午2:12:57
 *
 */
public enum ContentTypeEnum {
    /***/
    JSON("json", ContentType.create("application/json", Consts.UTF_8)),
    /***/
    XML("xml", ContentType.create("text/xml", Consts.UTF_8)),
    /***/
    FORM_URL("form_url", ContentType.create("application/x-www-form-urlencoded", Consts.UTF_8)),
    /***/
    TEXT("text", ContentType.create("text/plain", Consts.UTF_8)),
    /***/
    ;

    /**类型key*/
    private String                                    key;

    /**类型*/
    private ContentType                               contentType;

    private static final Map<String, ContentTypeEnum> CONTENTTYPE_MAPPER = new HashMap<>(Numbers.INT_16);
    static {
        for (ContentTypeEnum thisEnum : ContentTypeEnum.values()) {
            CONTENTTYPE_MAPPER.put(thisEnum.getKey(), thisEnum);
        }
    }

    ContentTypeEnum(String key, ContentType contentType) {
        this.key = key;
        this.contentType = contentType;
    }

    public static ContentTypeEnum getEnum(String key) {
        if (key == null) {
            return null;
        }
        return CONTENTTYPE_MAPPER.get(key);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

}
