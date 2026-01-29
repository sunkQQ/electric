package com.electric.model.response.jiaofei.tucheng;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author sunk
 * @date 2024/11/13
 */
@Getter
@Setter
@ToString
public class JiaofeiTuChengDebtResult {

    // 馆代码
    private String libcode;

    // 欠款类型
    private String feetype;

    // 欠款额
    private BigDecimal fee;

    // 图书条码
    private String barcode;

    // 书名
    private String title;

    // 作者
    private String author;

    // ISBN
    private String isbn;

    // 出版社
    private String publisher;

    // 索书号
    private String callno;

    // 借书时间
    private String loandate;

    // 欠款发生时间
    private String regtime;

    // 唯一ID
    private String tranid;
}
