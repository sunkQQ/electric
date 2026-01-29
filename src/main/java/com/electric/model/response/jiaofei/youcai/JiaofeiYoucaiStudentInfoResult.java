package com.electric.model.response.jiaofei.youcai;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 有财缴费学生信息返回 *
 *
 * @author sunk
 * @date 2022/08/05
 */
@Getter
@Setter
@ToString
public class JiaofeiYoucaiStudentInfoResult extends JiaofeiYoucaiBaseResult {

    /**
     * 学号
     */
    @JSONField(name = "studentCode")
    private String studentCode;

    /**
     * 身份证号
     */
    @JSONField(name = "Id_Card")
    private String idCard;

    /**
     * 姓名
     */
    @JSONField(name = "StudentName")
    private String studentName;

    /**
     * 性别：男、女
     */
    @JSONField(name = "Sexual")
    private String sexual;

    public JiaofeiYoucaiStudentInfoResult(String respcode, String respdesc) {
        super(respcode, respdesc);
    }
}
