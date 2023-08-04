package com.electric.response.shuangqi;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 双旗公寓查询学生信息返回字段
 *
 * @author sunk
 * @date 2023/08/03
 */
@Getter
@Setter
@ToString
public class ShuangqiUserPersonInfo {

    /** 人员编号 N */
    private String personId;

    /** 姓名 N */
    private String name;

    /** 学生类型（可配置） N */
    private String studentType;

    /** 性别（0：未知、1：男、2：女） N */
    private String sex;

    /** 手机号（加密） N */
    private String phone;

    /** 照片 N */
    private String photoId;

    /** 校区 N */
    private String schoolCampus;

    /** 学院 N */
    private String schoolInstituteId;

    /** 专业 N */
    private String schoolMajorId;

    /** 班级 N */
    private String schoolClassId;

    /** 年级 N */
    private String grade;

    /** 学制 N */
    private String schoolingLength;

    /** 书院 N */
    private String classicalCollege;

    /** 民族 N */
    private String nation;

    /** 籍贯 N */
    private String nativePlace;

    /** 一卡通卡号 N */
    private String cardNumber;

    /** 一卡通版本号 N */
    private String cardVersion;

    /** 卡状态（0：销卡、1：有效、2：挂失、3：冻结、4：预销卡、5：锁卡） N */
    private String cardStatus;

    /** 人脸版本号 N */
    private String faceVersion;

    /** 备注 N */
    private String remark;

    /** 住宿资源全称 N */
    private String dormitoryInfo;

    /** 住宿日期 N */
    private String checkInDate;

    /** 离宿日期 N */
    private String checkOutDate;

    /** 楼栋编号 N */
    private String buildingCode;

    /** 房间编号 N */
    private String roomCode;
}
