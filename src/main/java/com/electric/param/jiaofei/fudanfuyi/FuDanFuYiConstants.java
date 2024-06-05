package com.electric.param.jiaofei.fudanfuyi;

/**
 * 复旦复翼缴费接口常量
 * @author yzh
 * @since 2020/5/21 11:08
 */
public class FuDanFuYiConstants {

    /**
     * 请求url
     */
    public static final String REQUEST_URL    = "/queryfee/queryfee.action";
    /**
     * 接口类型 查询费用
     */
    public static final String QUERY          = "SH02";
    /**
     * 接口类型 缴费结果
     */
    public static final String NOTICE         = "SH03";
    /**
     * 
     */
    public static final String API_VERSION    = "1.0.0.6";
    /**
     * 查询方式 学号
     */
    public static final String USER_NO_TYPE   = "1";
    /**
     * 查询方式 身份证
     */
    public static final String ID_CARD_TYPE   = "2";

    // ----------------------- 参数 -------------------------------

    public static final String FEE_ITEM       = "feeitem";
    public static final String VERSION        = "version";
    public static final String TRANSCODE      = "transcode";

    public static final String QUERY_FEE_TYPE = "queryfeetype";
    public static final String USER_ID        = "userid";
    public static final String ID_NO          = "idno";
    public static final String IDCARD_NO      = "idcardno";

    public static final String APPSEQ_NO      = "appseqno";
    public static final String ACC            = "acc";
    public static final String ACC_DATE       = "accdate";
    public static final String PAY_BANK_ID    = "paybankid";
    public static final String USER_NAME      = "username";
    public static final String PHONE          = "phone";
    public static final String EMAIL          = "email";
    public static final String REMARK         = "remark";

    public static final String FEES           = "fees";
    public static final String FEE            = "fee";
    public static final String FEE_ITEM_CODE  = "feeitemdeford";
    public static final String FEE_ORDER      = "feeord";
    public static final String FEE_AMOUNT     = "feeamt";
    public static final String PAID_AMT       = "paidamt";

    // ----------------------- 返回 -------------------------------

    public static final String RESP_CODE      = "respcode";
    public static final String RESP_MSG       = "respinfo";
    public static final String RESP_DATE      = "respdate";
    public static final String RESP_TIME      = "resptime";

    public static final String FEE_COUNT      = "feecnt";
    public static final String FEE_ITME_NAME  = "feeitemname";

    public static final String RESP_SEQ_NO    = "respseqno";
}
