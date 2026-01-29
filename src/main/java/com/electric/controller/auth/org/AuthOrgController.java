package com.electric.controller.auth.org;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.electric.model.param.auth.AuthOrgDepartmentParam;
import com.electric.model.param.auth.AuthOrgUserParam;
import com.electric.util.HttpServletRequestUtil;
import com.electric.util.RsaUtil3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 基础信息同步
 *
 * @author sunk
 * @date 2023/04/10
 */
@Slf4j
@RestController
@RequestMapping("/api/common/system")
public class AuthOrgController {

    private static final String KEY        = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJJg3nrXRJ7zCdanoJCcyQycK8CR04K2JXF9RZKDS3uF1jeNuuT6Fl75VVPUxXt/4QTgeBJdm9Y1khaFnOhTqAcCAwEAAQ==";
    private static final String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJiTFaZW1POH2Z4Mxg8ln70SljHCH6dNsZWjLmM2hvEDPQ9uUla5TvCD24iWnKjzuH4BgG9Ga/KekMulGdBF5qZjatU0Gn3P2emaeHRB7inpmCh/QgrzuGGLjMpMrfepaCi0eJGQ7nQEO64vtoZ2PdqTa9z2K7U3C+60pokTFBXJAgMBAAECgYBjfNVLJ6eoPNcr9gxX6CKm+S2yuHBHeaPofQVMcEvBcGkqXLeYETFmJ7TI9dylVnbgwz3qUTMmezrTb47LfikNKC4DwNbrC11mAaXnRTu2Vxi3YXm8e+ex0xp5sdBsJpxzRc7kB34tD+uhT00G02Cl7mBJLqqmy+m0/Y4tZT1gOQJBAM01HEJiwvxyrbf4DwSKyXWU5ygEJN1t2s8j+i6iZq80hvB7OToer1JNyfB/ykDisXL9T4Zi3m+YDs2deU2fEMsCQQC+VukUQVJD3ft32K27Byw/KGTNvXYyoybuLVWDW0npSvKi67vdHlAkc88SHW4sU1RDs/AJDDMshak/yMoRncU7AkEAt6CgAhPIcgjYxPg30BO1hP9S/m7+4hqMo7GJDbQLeRri6U0K/6Q3tMmhPBHOVdPFgKWGRTYx3+BM1tfklX4SkwJAO9ZgwfBjJ1jS5HNm8oQnwdc2EYP9c5/c7kWsO3ZofwID03oefj44Xa1WrNA5wY+Uzw/zZzTBpb6DXb0G4ZDi3wJAQgiop+Lk79Gs5mfX522g8azQSua1tUvoS1hKglYjR2B8GAc4QVnKIhQHEGSxtnyZqvkoyvsJuCWN/DMTaxDZjQ==";

    /**
     * 获取基础数据部门信息数据
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/getDepartment", method = RequestMethod.POST)
    public String getDepartment(HttpServletRequest request, AuthOrgDepartmentParam param) {
        Map<String, String> map = HttpServletRequestUtil.getRequestParmeter(request);

        JSONObject jsonObject = new JSONObject();
        /*String str = SignUtil.getSignParams(map);
        String signParams = Md5Util.md5(str);
        String sign = null;
        try {
            sign = RsaUtil3.decryptByPrivateKey(param.getSign(), privateKey);
        } catch (Exception e) {
            throw new BizException("RSA加密失败", e);
        }
        
        if (!sign.equalsIgnoreCase(signParams)) {
            log.info(sign);
            jsonObject.put("code", -1);
            jsonObject.put("msg", "签名不正确");
            return jsonObject.toString();
        }*/
        jsonObject.put("code", "0");

        JSONObject json = new JSONObject();
        json.put("count", 3);
        json.put("lastUpdateTime", "2023-04-13 19:00:00");
        JSONArray array = new JSONArray();
        /*int count = 1000;
        if (param.getPageNo() == 1) {
            count = count - 3;*/

        JSONObject deptJson3 = new JSONObject();
        deptJson3.put("accDepNum", "100101001" + param.getDepType());
        deptJson3.put("accDepName", "测试子部门101001" + param.getDepType());
        deptJson3.put("aliasName", "101001");
        deptJson3.put("parentNum", "100101" + param.getDepType());
        deptJson3.put("parentName", "子部门101" + param.getDepType());
        deptJson3.put("degree", 1);
        deptJson3.put("level", 2);
        deptJson3.put("entranceYear", 2023);
        deptJson3.put("entranceTerm", 1);
        deptJson3.put("fullTimeType", 0);
        deptJson3.put("organizationIntroduce", "这是个部门简介101001");
        deptJson3.put("depType", param.getDepType());
        array.add(deptJson3);
        JSONObject deptJson1 = new JSONObject();
        deptJson1.put("accDepNum", "1001" + param.getDepType());
        deptJson1.put("accDepName", "测试部门1" + param.getDepType());
        deptJson1.put("aliasName", "1");
        deptJson1.put("parentNum", "0");
        deptJson1.put("parentName", "0");
        deptJson1.put("level", 0);
        deptJson1.put("degree", 1);
        deptJson1.put("entranceYear", 2023);
        deptJson1.put("entranceTerm", 1);
        deptJson1.put("fullTimeType", 0);
        deptJson1.put("organizationIntroduce", "这是个部门简介");
        deptJson1.put("depType", param.getDepType());
        array.add(deptJson1);
        JSONObject deptJson2 = new JSONObject();
        deptJson2.put("accDepNum", "100101" + param.getDepType());
        deptJson2.put("accDepName", "子部门101" + param.getDepType());
        deptJson2.put("aliasName", "101");
        deptJson2.put("parentNum", "1001" + param.getDepType());
        deptJson2.put("parentName", "测试部门1" + param.getDepType());
        deptJson2.put("degree", 1);
        deptJson2.put("level", 1);
        deptJson2.put("entranceYear", 2023);
        deptJson2.put("entranceTerm", 1);
        deptJson2.put("fullTimeType", 0);
        deptJson2.put("organizationIntroduce", "这是个部门简介101");
        deptJson2.put("depType", param.getDepType());
        array.add(deptJson2);
        /*} else if (param.getPageNo() == 3) {
            count = 3;
        }
        
        for (int i = 0; i < count; i++) {
            JSONObject deptJson4 = new JSONObject();
            deptJson4.put("accDepNum", "100101001" + param.getDepType() + param.getPageNo() + "00" + i);
            deptJson4.put("accDepName", "测试子部门101001" + param.getDepType() + param.getPageNo() + "00" + i);
            deptJson4.put("aliasName", "101001" + param.getDepType() + param.getPageNo() + "00" + i);
            deptJson4.put("parentNum", "100101" + param.getDepType());
            deptJson4.put("parentName", "子部门101" + param.getDepType());
            deptJson4.put("degree", 1);
            deptJson4.put("level", 2);
            deptJson4.put("entranceYear", 2023);
            deptJson4.put("entranceTerm", 1);
            deptJson4.put("fullTimeType", 0);
            deptJson4.put("organizationIntroduce", "这是个部门简介101001");
            deptJson4.put("depType", param.getDepType());
        
            array.add(deptJson4);
        }*/

        json.put("list", array);
        log.info("listSize:{}", array.size());
        jsonObject.put("data", json);
        return jsonObject.toString();
    }

    @RequestMapping(value = "/getAccInfo", method = RequestMethod.POST)
    public String getAccInfo(HttpServletRequest request, AuthOrgUserParam param) {
        Map<String, String> map = HttpServletRequestUtil.getRequestParmeter(request);

        JSONObject jsonObject = new JSONObject();

        /* String sign = null;
        String signParams = Md5Util.md5(SignUtil.getSignParams(map));
        try {
            sign = RsaUtil3.decryptByPrivateKey(param.getSign(), privateKey);
        } catch (Exception e) {
            log.error("RSA加密失败", e);
            throw new BizException("RSA加密失败");
        }
        if (!sign.equalsIgnoreCase(signParams)) {
            log.info(sign);
            jsonObject.put("code", -1);
            jsonObject.put("msg", "签名不正确");
            return jsonObject.toString();
        }*/
        jsonObject.put("code", "0");

        JSONObject json = new JSONObject();
        json.put("count", 2);
        json.put("lastUpdateTime", "2023-04-13 19:00:00");
        JSONArray array = new JSONArray();
        //int count = 1000;
        //if (param.getPageNo() == 1) {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("jobNo", "YM201" + param.getUserType());
        userJson1.put("userName", "云马201" + param.getUserType());
        //userJson1.put("mobilePhone", "15600000001");
        userJson1.put("userType", param.getUserType());
        userJson1.put("sex", 1);
        userJson1.put("schoolStatus", 1);
        userJson1.put("enrolTime", "2023-04-10");
        userJson1.put("offSchoolTime", "2024-04-10");
        userJson1.put("entranceYear", 2024);
        userJson1.put("departNum", "10011");
        userJson1.put("specialitiesNum", "1001011");
        userJson1.put("classNum", "1001010011");
        userJson1.put("teacherDepartmentNums", "1001010012,1001012");
        userJson1.put("term", 1);
        userJson1.put("degree", 1);
        userJson1.put("academicSystem", 4);
        userJson1.put("fullTimeType", 1);
        array.add(userJson1);

        JSONObject userJson2 = new JSONObject();
        userJson2.put("jobNo", "YM202" + param.getUserType());
        userJson2.put("userName", "云马202" + param.getUserType());
        //userJson2.put("mobilePhone", "15600000002");
        userJson2.put("userType", param.getUserType());
        userJson2.put("sex", 1);
        userJson2.put("schoolStatus", 1);
        userJson2.put("enrolTime", "2023-04-10");
        userJson2.put("offSchoolTime", "2024-04-10");
        userJson2.put("departNum", "10011");
        userJson2.put("specialitiesNum", "1001011");
        userJson2.put("classNum", "1001010011");
        userJson2.put("teacherDepartmentNums", "1001010012,1001012");
        userJson2.put("entranceYear", 2024);
        userJson2.put("term", 1);
        userJson2.put("degree", 1);
        userJson2.put("academicSystem", 4);
        userJson2.put("fullTimeType", 1);
        array.add(userJson2);

        /*count = count - 2;
        } else if (param.getPageNo() == 3) {
        count = 2;
        }
        
        for (int i = 0; i < count; i++) {
        JSONObject userJson3 = new JSONObject();
        userJson3.put("jobNo", "YM202" + param.getUserType() + param.getPageNo() + "00" + i);
        userJson3.put("userName", "云马202" + param.getUserType() + param.getPageNo() + "00" + i);
        //userJson3.put("mobilePhone", "15600000002");
        userJson3.put("userType", param.getUserType());
        userJson3.put("sex", 1);
        userJson3.put("schoolStatus", 1);
        userJson3.put("enrolTime", "2023-04-10");
        userJson3.put("offSchoolTime", "2024-04-10");
        userJson3.put("departNum", "10011");
        userJson3.put("specialitiesNum", "1001011");
        userJson3.put("classNum", "1001010011");
        userJson3.put("teacherDepartmentNums", "1001010012,1001012");
        userJson3.put("entranceYear", 2024);
        userJson3.put("term", 1);
        userJson3.put("degree", 1);
        userJson3.put("academicSystem", 4);
        userJson3.put("fullTimeType", 1);
        array.add(userJson3);
        }*/

        json.put("list", array);
        jsonObject.put("data", json);
        log.info("listSize:{}", array.size());
        return jsonObject.toString();
    }

    private void successDept() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("code", "0");

        JSONObject json = new JSONObject();
        json.put("count", 3);
        JSONArray array = new JSONArray();
        JSONObject deptJson1 = new JSONObject();
        deptJson1.put("accDepNum", "1001");
        deptJson1.put("accDepName", "部门1");
        deptJson1.put("aliasName", "1");
        deptJson1.put("parentNum", "0");
        deptJson1.put("parentName", "0");
        deptJson1.put("degree", 1);
        deptJson1.put("entranceYear", 2023);
        deptJson1.put("entranceTerm", 1);
        deptJson1.put("fullTimeType", 0);
        deptJson1.put("organizationIntroduce", "这是个部门简介");
        array.add(deptJson1);
        JSONObject deptJson2 = new JSONObject();
        deptJson2.put("accDepNum", "100101");
        deptJson2.put("accDepName", "部门101");
        deptJson2.put("aliasName", "101");
        deptJson2.put("parentNum", "1001");
        deptJson2.put("parentName", "部门1");
        deptJson2.put("degree", 1);
        deptJson2.put("entranceYear", 2023);
        deptJson2.put("entranceTerm", 1);
        deptJson2.put("fullTimeType", 0);
        deptJson2.put("organizationIntroduce", "这是个部门简介101");
        array.add(deptJson2);
        JSONObject deptJson3 = new JSONObject();
        deptJson3.put("accDepNum", "100101001");
        deptJson3.put("accDepName", "部门101001");
        deptJson3.put("aliasName", "101001");
        deptJson3.put("parentNum", "100101");
        deptJson3.put("parentName", "部门101");
        deptJson3.put("degree", 1);
        deptJson3.put("entranceYear", 2023);
        deptJson3.put("entranceTerm", 1);
        deptJson3.put("fullTimeType", 0);
        deptJson3.put("organizationIntroduce", "这是个部门简介101001");
        array.add(deptJson3);
        json.put("list", array);

        jsonObject.put("data", json);
    }

    public static void main(String[] args) {
        String sign = "cdB4LsrqPzhYteP/9iaVj7weOn7CiQ1EpBPaAg+GcF4MTZPtKNIOoPCv+aIMbDn9WBrHq7ZKGYSNdOQLmYIx9u/cccbYWPf8yDwptyLCc7+v+OOA3YaovgyICdAhBdZBEM0ezV2P9f2h4YRs2kwdHFjloIujucqws645i26jFVI=";
        /*Map<String, String> map = new HashMap<String, String>();
        map.put("depType", "1");
        map.put("lastUpdateTime", "2023-04-13 19:00:00");
        map.put("pageNo", "1");
        map.put("pageSize", "1000");
        map.put("sign", sign);
        String str = SignUtil.getSignParams(map);
        System.out.println(str);
        String signParams = Md5Util.md5(str);
        System.out.println(signParams);*/

        try {
            String sourceStr1 = RsaUtil3.decryptByPrivateKey(sign, privateKey);
            System.out.println(sourceStr1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void successUser() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("code", 0);

        JSONObject json = new JSONObject();
        json.put("count", 2);

        JSONArray array = new JSONArray();
        JSONObject userJson1 = new JSONObject();
        userJson1.put("jobNo", "YM201");
        userJson1.put("userName", "云马201");
        userJson1.put("mobilePhone", "15600000001");
        userJson1.put("userType", 1);
        userJson1.put("sex", 1);
        userJson1.put("schoolStatus", 1);
        userJson1.put("enrolTime", "2023-04-10");
        userJson1.put("offSchoolTime", "2024-04-10");
        userJson1.put("entranceYear", 2024);
        userJson1.put("term", 1);
        userJson1.put("degree", 1);
        userJson1.put("academicSystem", 4);
        userJson1.put("fullTimeType", 1);
        array.add(userJson1);

        JSONObject userJson2 = new JSONObject();
        userJson2.put("jobNo", "YM202");
        userJson2.put("userName", "云马202");
        userJson2.put("mobilePhone", "15600000002");
        userJson2.put("userType", 1);
        userJson2.put("sex", 1);
        userJson2.put("schoolStatus", 1);
        userJson2.put("enrolTime", "2023-04-10");
        userJson2.put("offSchoolTime", "2024-04-10");
        userJson2.put("entranceYear", 2024);
        userJson2.put("term", 1);
        userJson2.put("degree", 1);
        userJson2.put("academicSystem", 4);
        userJson2.put("fullTimeType", 1);
        array.add(userJson2);
        json.put("list", array);
        jsonObject.put("data", json);

        System.out.println(jsonObject);
    }
}
