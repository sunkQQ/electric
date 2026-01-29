package com.electric.model.response.special.kayroad;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 凯路水控   — 趣智校园外部【注册/查询⽤户信息】接口返回数据
 * 
 * @author sunk
 * @Data 2020年5月21日
 */
@Getter
@Setter
@ToString
public class KayRoadQuZhiUserResponse {

    /** 项目ID */
    private String projectId;

    /** 项目名称 */
    private String projectName;

    /** 用户ID  */
    private String accountId;

    /** 手机号 */
    private String telPhone;

    /** 学号、证件号 */
    private String identifier;

    /** 姓名 */
    private String name;

    /** 性别 */
    private String sex;

    /** 账户状态 0正常 */
    private String statusId;

    /** 现金金额（厘） */
    private String accountMoney;

    /** 赠送金额（厘） */
    private String accountGivenMoney;

    /** 别名 */
    private String alias;

    /** 标签 */
    private String tags;

    /** 是否已绑卡 */
    private String isCard;

    /** 卡状态  */
    private String cardStatusId;

    /** 是否设置使用码 */
    private String isUseCode;
}
