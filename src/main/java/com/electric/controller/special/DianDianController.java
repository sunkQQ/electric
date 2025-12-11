package com.electric.controller.special;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONObject;
import com.electric.param.special.DianDianParam;
import com.electric.response.special.diandian.DiandianAccInfoResult;
import com.electric.util.AesUtil;

/**
 * 点点饭卡
 *
 * @author sunkang
 * 2025/11/3
 */
@RestController
@RequestMapping("/open/user")
public class DianDianController {

    @RequestMapping(value = "/getAccByPhone", method = RequestMethod.POST)
    public String user(HttpServletRequest request, DianDianParam param) {
        DiandianAccInfoResult result = new DiandianAccInfoResult();
        result.setSuccess(true);
        result.setMessage("success");
        result.setCode("200");
        result.setTimestamp(System.currentTimeMillis() + "");
        result.setErrCode("0");

        List<DiandianAccInfoResult.AccInfo> accInfoList = new ArrayList<>();
        DiandianAccInfoResult.AccInfo accInfo = new DiandianAccInfoResult.AccInfo();
        BigInteger bigDecimalNumber = new BigInteger(param.getPhones()); // 十进制大数
        String hexString = bigDecimalNumber.toString(16);
        accInfo.setAccount(hexString);
        accInfo.setPhone(param.getPhones());
        accInfoList.add(accInfo);

        result.setResult(AesUtil.encryptEcb(JSONObject.toJSONString(accInfoList), "123456789abcdefg"));
        return JSONObject.toJSONString(result);
    }

    public static void main(String[] args) {
        String aes = AesUtil.encryptEcb("{\"account\":\"123456\",\"phone\":\"123456\"}", "123456789abcdefg");
        String str = AesUtil.decryptEcb(aes, "123456789abcdefg");
        System.out.println(aes);
        System.out.println(str);
    }
}
