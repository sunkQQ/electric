package com.electric.model.param.invoice;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 单张票据开具参数
 *
 * @author sunk
 * @date 2022/07/04
 */
@Getter
@Setter
@ToString
public class BosssoftInvoiceParam {

    /**
     * 业务种类  必填
     *      分配的 appid
     */
    private String    busType;

    /**
     * 业务单号  必填
     *      全局唯一，也叫业务流水号
     */
    private String    busNo;

    /**
     * 开票点编码  必填
     *     需要业务系统和平台约定对照关系
     */
    private String    placeCode;

    /**
     * 票据种类编码  必填
     *      需要业务系统和平台约定对照关系
     */
    private String    billCode;

    /**
     * 收费渠道   必填
     *      需要业务系统和平台约定对照关系直接填写业务系统内部编码值
     */
    private String    channel;

    /**
     * 收费期间
     *      格式：yyyy，如 2017
     */
    private String    chargePeriod;

    /**
     * 收费日期
     *      格式：yyyy-MM-dd
     */
    private String    chargeDate;

    /**
     * 缴款人类型  必填
     *      交款人类型：1 个人 2 单位，默认为1
     */
    private String    payerType;

    /**
     * 单位机构编码
     *      交款人类型=2,填写值如：统一社会信用代码
     */
    private String    creditCode;

    /**
     * 收款人   必填
     */
    private String    recer;

    /**
     * 开票日期     必填
     *      格式：yyyy-MM-dd
     */
    private String    ivcDate;

    /**
     * 学生姓名(缴款人)    必填
     */
    private String    stuName;

    /**
     * 开票人      必填
     */
    private String    operator;

    /**
     * 合计金额     必填
     */
    private String    totalAmt;

    /**
     * 备注
     */
    private String    memo;

    /**
     * 学生学号
     */
    private String    stuNo;

    /**
     * 学院编码
     */
    private String    collegeCode;

    /**
     * 学院名称
     */
    private String    collegeName;

    /**
     * 系别编码
     */
    private String    deptCode;

    /**
     * 系别名称
     */
    private String    deptName;

    /**
     * 专业编码
     */
    private String    proCode;

    /**
     * 专业名称
     */
    private String    proName;

    /**
     * 班级编码
     */
    private String    classCode;

    /**
     * 班级名称
     */
    private String    className;

    /**
     * 学生身份证
     */
    private String    stuidCard;

    /**
     * 入学年度
     *      格式：yyyy，如 2017
     */
    private String    inYear;

    /**
     * 离校年度
     *      格式：yyyy，如 2017
     */
    private String    outYear;

    /**
     * 手机号码
     */
    private String    phone;

    /**
     * 邮箱地址
     */
    private String    email;

    /**
     * 学生支付宝账户
     *      学生如果使用支付宝结，可传入学生的支付宝UserID，
     *      用于电子票据归集到支付宝发票管家
     */
    private String    alipayCode;

    /**
     * 微信支付订单号
     *      微信结算时，可传入学生支付成功的订单号，用来发微信服务通知
     */
    private String    weChatOrderNo;

    /**
     * 微信公众号或小程序用户 ID
     */
    @JSONField(name = "openID")
    private String    openId;

    /**
     * 通知方式
     *      0 无需通知，1 手机通知， 2 邮箱通知，3 两者都通知
     */
    private String    noticeMode;

    /**
     * 复核人
     */
    private String    reViewer;

    /**
     * 拓展字段1
     */
    private String    extendedField1;

    /**
     * 拓展字段2
     */
    private String    extendedField2;

    /**
     * 拓展字段3
     */
    private String    extendedField3;

    /**
     * 拓展字段4
     */
    private String    extendedField4;

    /**
     * 拓展字段5
     */
    private String    extendedField5;

    /**
     * 收费项目明细
     *      详见 A-1 ，JSON  格式列表
     */
    private JSONArray itemDetail;

    /**
     * 支付信息列表
     *      填写费用支付类型信息，支付类型不在【患者支付宝账户、微信支付订单号、微信医保支付订单号、微信公众号或小程序用户ID、银联支付信息】范围内容的，
     *      其它支付类型信息可通过列表方式传入详见 见A-2,JSON 格式列表
     */
    private String    payOrderInfo;

    /**
     * 收费项目明细
     */
    @Getter
    @Setter
    @ToString
    public static class ItemDetail {

        /**
         * 收费项目编码       必填
         *      需要业务系统和平台约定对照关系
         */
        private String itemCode;

        /**
         * 收费项目名称
         */
        private String itemName;

        /**
         * 收费标准编码
         *      需要业务系统和平台约定对照关系
         */
        private String itemStdCode;

        /**
         * 数量
         */
        private String count;

        /**
         * 收费标准
         *      如有传入收费标准编码，收费标准必须在规定的范
         */
        private String standard;

        /**
         * 金额
         */
        private String amt;

        /**
         * 计量单位
         */
        private String units;

        /**
         * 备注 1
         */
        private String memo1;

        /**
         * 备注 2
         */
        private String memo2;
    }

    /**
     * 支付信息列表
     */
    @Getter
    @Setter
    @ToString
    public static class PayOrderInfo {

        /**
         * 支付类型编码
         *      参考附录 6 中 6.5支付类型编码列表
         */
        private String payOrderCode;

        /**
         * 支付类型值
         */
        private String payOrderValue;
    }
}
