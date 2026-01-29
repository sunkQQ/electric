package com.electric.model.param.jiaofei.fudanfuyi;

import lombok.ToString;

/**
 * 复旦复翼待缴费
 * @author yzh
 * @since 2020/5/21 15:48
 */
@ToString
public class FuDanFuYiFeeVO {

    /**
     * 学号
     */
    private String userId;
    /**
     * 姓名
     */
    private String userName;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 费用项代码
     */
    private String itemCode;
    /**
     * 费用项名称
     */
    private String itemName;
    /**
     * 费用项序号
     */
    private String orderNo;
    /**
     * 费用项金额
     */
    private String feeAmount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }
}
