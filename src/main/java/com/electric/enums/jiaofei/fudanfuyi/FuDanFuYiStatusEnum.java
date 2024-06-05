package com.electric.enums.jiaofei.fudanfuyi;

import java.util.Arrays;

/**
 * 复旦复翼接口返回状态
 * @author yzh
 * @since 2020/5/21 14:58
 */
public enum FuDanFuYiStatusEnum {
    /** */
    SUCCESS("0000", "成功"),
    /** */
    E0001("0001", "校验用户名不正确"),
    /** */
    E0002("0002", "内部错误"),
    /** */
    E0003("0003", "没有费用项"),
    /** */
    E0004("0004", "token值错误"),
    /** */
    E0005("0005", "缴费银行不能为空"),
    /** */
    E0006("0006", "验签失败"),
    /** */
    E0008("0008", "勿重复推送"),
    /** */
    E0009("0009", "交易金额不能小于0"),
    /** */
    E0010("0010", "支付方式不存在"),
    /** */
    E0011("0011", "费用项代码不存在"),
    /** */
    E0012("0012", "用户名不能为空"),
    /** */
    E0013("0013", "电话号码不正确"),
    /** */
    E0014("0014", "邮箱不合法"),;

    private String code;
    private String name;

    FuDanFuYiStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static FuDanFuYiStatusEnum getEnum(String code) {
        return Arrays.stream(values()).filter(item -> item.code.equals(code)).findAny().orElse(null);
    }

    public static String getName(String code) {
        FuDanFuYiStatusEnum item = getEnum(code);
        return item == null ? "" : item.getName();
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
