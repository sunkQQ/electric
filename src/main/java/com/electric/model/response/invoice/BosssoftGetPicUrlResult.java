package com.electric.model.response.invoice;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 博思开票查询票据页面
 *
 * @author sunk
 * @date 2022/07/26
 */
@Getter
@Setter
@ToString
public class BosssoftGetPicUrlResult {

    /** 电子票据H5页面URL */
    private String pictureUrl;
}
