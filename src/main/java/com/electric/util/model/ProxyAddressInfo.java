package com.electric.util.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * 代理地址信息详情
 *
 * @author sunk
 * @date 2024/09/11
 */
@Getter
@Setter
public class ProxyAddressInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**被代理的地址*/
    private String            url;
    /**校验次数*/
    private Integer           checkCount       = 0;
    /**代理ip*/
    private String            proxyIp;
    /**是否固定值*/
    private Boolean           fixed            = false;

    public ProxyAddressInfo() {

    }

    public ProxyAddressInfo(String url, Integer checkCount, String proxyIp) {
        if (StringUtils.isEmpty(url)) {
            throw new RuntimeException("参数异常");
        }
        this.url = url;
        this.checkCount = checkCount;
        this.proxyIp = proxyIp;
    }

}
