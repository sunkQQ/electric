package com.electric.response.jiaofei.chaoxing;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 超星图书馆接在借出信息接口返回
 *
 * @author sunk
 * @String 2023/05/04
 */
@Getter
@Setter
@ToString
public class ChaoXingLibraryLendingResult {

    /** 借阅ID */
    private Long    loanId;
    /** 用户ID */
    private Integer userId;
    /** 用户所属成员馆代码 */
    private String  userLibCode;
    /** 用户所属馆名称 */
    private String  userLibName;
    /** 应还日期 */
    private String  normReturnDate;
    /** 续借次数 */
    private Integer renewTimes;
    /** 催还次数 */
    private Integer recallTimes;
    /** 借出日期 */
    private String  loanDate;
    /** 馆藏地id */
    private Integer locationId;
    /** 馆藏地名称 */
    private String  locationName;
    /** 图书所属分馆代码 */
    private String  itemLibCode;
    /** 图书所属分馆名称 */
    private String  itemLibName;
    /** 借书流通台id */
    private Long    loanDeskId;
    /** 借书流通台名称 */
    private String  loanDeskName;
    /** 附件 */
    private String  attachment;
    /** 续借日期 */
    private String  renewDate;
    /** 操作类型 0表示借阅 1表示续借 2表示催还  */
    private String  loanType;
    /** 题名 */
    private String  title;
    /** 作者 */
    private String  author;
    /** 出版社 */
    private String  publisher;
    /** isbn */
    private String  isbn;
    /** 出版年 */
    private String  publishYear;
    /** isbn10位 */
    private String  isbn10;
    /** isbn13位 */
    private String  isbn13;
    /** 图书条码号 */
    private String  barcode;
    /** 元数据记录号 */
    private String  recordId;
}
