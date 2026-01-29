package com.electric.model.enums;

/**
 * 星期枚举类
 * 
 * @Author Administrator
 * @Date 2020-9-10
 *
 */
public enum WeekEnum {

    MONDAY("1", "周一"),
    TUESDAY("2", "周二"),
    WEDNESDAY("3", "周三"),
    THURSDAY("4", "周四"),
    FRIDAY("5", "周五"),
    SATURDAY("6", "周六"),
    SUNDAY("7", "周日");

    private String code;
    private String name;

    WeekEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 根据code获取name
     *
     * @param code
     * @return name
     */
    public static String getNameByCode(String code) {
        for (WeekEnum thisEnum : WeekEnum.values()) {
            if (thisEnum.getCode().equals(code)) {
                return thisEnum.getName();
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public Integer getCodeToInt() {
        return Integer.valueOf(code);
    }

    public String getName() {
        return name;
    }

    public static String getName(String code) {
        if (code == null) {
            return null;
        }
        WeekEnum thisEnum = getEnum(code);
        return thisEnum == null ? null : thisEnum.name;
    }

    public static WeekEnum getEnum(String code) {
        if (code == null) {
            return null;
        }
        for (WeekEnum thisEnum : WeekEnum.values()) {
            if (thisEnum.code.equals(code)) {
                return thisEnum;
            }
        }
        return null;
    }

}
