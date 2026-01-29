package com.electric.model.param.jiaofei.chaoxing;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 超星图书馆在借信息查询
 *
 * @author sunk
 * @date 2023/04/27
 */
@Getter
@Setter
@ToString
public class ChaoXingLibraryLendingParam {

    /**
     * 读者条码
     *      patronBarCode和primaryId二选一，优先匹配patronBarCode
     */
    private String patronBarCode;

    /**
     * 读者所在馆
     */
    private String patronLibCode;

    /**
     * 读者账号
     */
    private String primaryId;
}
