package com.electric.response.net.cityhot;

import java.io.Serializable;

/** 
 * 城市热点v5.0.0版本账号信息返回字段
* @author sunk
* @version 2019年8月21日
* 
*/
public class CityHotV5AccountInfoResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    private String            account;

    /**
     * 开户日期    格式：yyyy-MM-dd
     */
    private String            register_date;

    /**
     * 起始计费时间   格式：yyyy-MM-dd hh:mm:ss
     */
    private String            start_date;

    /**
     * 可用状态    0=停机，1=正常，2=临时停机
     */
    private Integer           user_state;

    /**
     * 状态时间     格式：yyyy-MM-dd hh:mm:ss
     */
    private String            state_time;

    /**
     *  状态备注     账号当前状态描述，如：停机
     */
    private String            state_memo;

    /**
     * 套餐组ID
     */
    private Integer           package_group_id;

    /**
     * 资费组ID
     */
    private Integer           billing_group_id;

    /**
     * 地区组ID
     */
    private Integer           area_group_id;

    /**
     * 带宽组ID
     */
    private Integer           bandwidth_group_id;

    /**
     * 资金账户余额      单位：元
     */
    private Double            balance;

    /**
     * 时长账户余额      单位：分钟
     */
    private Double            left_time;

    /**
     * 流量账户余额      单位：MB
     */
    private Double            left_flow;

    /**
     * 本期资金消费      单位：元
     */
    private Double            use_money;

    /**
     * 本期时长消费      单位：分钟，本期纯时长消费（不包含资金换算的）
     */
    private Double            use_time_only;

    /**
     * 本期流量消费      单位：MB，本期纯流量消费（不包含资金换算的）
     */
    private Double            use_flow_only;

    /**
     * 本期累计使用时长    单位：分钟
     */
    private Double            use_time;

    /**
     * 本期累计使用流量    单位：MB
     */
    private Double            use_flow;

    /**
     * 可用时长    单位：分钟，包含资金账户余额转换后的时长
     */
    private Double            available_time;

    /**
     * 可用流量    单位：MB，包含资金账户余额转换后的流量
     */
    private Double            available_flow;

    /**
     * 姓名
     */
    private String            name;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRegister_date() {
        return register_date;
    }

    public void setRegister_date(String register_date) {
        this.register_date = register_date;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public Integer getUser_state() {
        return user_state;
    }

    public void setUser_state(Integer user_state) {
        this.user_state = user_state;
    }

    public String getState_time() {
        return state_time;
    }

    public void setState_time(String state_time) {
        this.state_time = state_time;
    }

    public String getState_memo() {
        return state_memo;
    }

    public void setState_memo(String state_memo) {
        this.state_memo = state_memo;
    }

    public Integer getPackage_group_id() {
        return package_group_id;
    }

    public void setPackage_group_id(Integer package_group_id) {
        this.package_group_id = package_group_id;
    }

    public Integer getBilling_group_id() {
        return billing_group_id;
    }

    public void setBilling_group_id(Integer billing_group_id) {
        this.billing_group_id = billing_group_id;
    }

    public Integer getArea_group_id() {
        return area_group_id;
    }

    public void setArea_group_id(Integer area_group_id) {
        this.area_group_id = area_group_id;
    }

    public Integer getBandwidth_group_id() {
        return bandwidth_group_id;
    }

    public void setBandwidth_group_id(Integer bandwidth_group_id) {
        this.bandwidth_group_id = bandwidth_group_id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getLeft_time() {
        return left_time;
    }

    public void setLeft_time(Double left_time) {
        this.left_time = left_time;
    }

    public Double getLeft_flow() {
        return left_flow;
    }

    public void setLeft_flow(Double left_flow) {
        this.left_flow = left_flow;
    }

    public Double getUse_money() {
        return use_money;
    }

    public void setUse_money(Double use_money) {
        this.use_money = use_money;
    }

    public Double getUse_time_only() {
        return use_time_only;
    }

    public void setUse_time_only(Double use_time_only) {
        this.use_time_only = use_time_only;
    }

    public Double getUse_flow_only() {
        return use_flow_only;
    }

    public void setUse_flow_only(Double use_flow_only) {
        this.use_flow_only = use_flow_only;
    }

    public Double getUse_time() {
        return use_time;
    }

    public void setUse_time(Double use_time) {
        this.use_time = use_time;
    }

    public Double getUse_flow() {
        return use_flow;
    }

    public void setUse_flow(Double use_flow) {
        this.use_flow = use_flow;
    }

    public Double getAvailable_time() {
        return available_time;
    }

    public void setAvailable_time(Double available_time) {
        this.available_time = available_time;
    }

    public Double getAvailable_flow() {
        return available_flow;
    }

    public void setAvailable_flow(Double available_flow) {
        this.available_flow = available_flow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CityHotV5AccountInfoResponse [account=" + account + ", register_date=" + register_date + ", start_date=" + start_date
               + ", user_state=" + user_state + ", state_time=" + state_time + ", state_memo=" + state_memo + ", package_group_id=" + package_group_id
               + ", billing_group_id=" + billing_group_id + ", area_group_id=" + area_group_id + ", bandwidth_group_id=" + bandwidth_group_id
               + ", balance=" + balance + ", left_time=" + left_time + ", left_flow=" + left_flow + ", use_money=" + use_money + ", use_time_only="
               + use_time_only + ", use_flow_only=" + use_flow_only + ", use_time=" + use_time + ", use_flow=" + use_flow + ", available_time="
               + available_time + ", available_flow=" + available_flow + ", name=" + name + "]";
    }

}
