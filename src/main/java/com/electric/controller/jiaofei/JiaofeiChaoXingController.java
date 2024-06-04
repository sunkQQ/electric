package com.electric.controller.jiaofei;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.electric.param.jiaofei.chaoxing.ChaoXingLibraryLendingParam;
import com.electric.param.jiaofei.chaoxing.ChaoXingNoticeParam;
import com.electric.response.jiaofei.chaoxing.ChaoXingBaseResult;
import com.electric.response.jiaofei.chaoxing.ChaoXingLibraryTokenResult;
import com.electric.response.jiaofei.chaoxing.ChaoxingUserDueDetailResult;
import com.electric.util.DateUtil;
import com.electric.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sunk
 * @date 2024/06/04
 */
@Slf4j
@RestController
public class JiaofeiChaoXingController {
    private final static List<ChaoxingUserDueDetailResult.DueDetailList> LIST = new ArrayList<>();
    static {
        ChaoxingUserDueDetailResult.DueDetailList detailList2 = new ChaoxingUserDueDetailResult.DueDetailList();
        detailList2.setBehaviourDetailId(StringUtil.generateString(6));
        detailList2.setBehaviourTypeId("3");
        detailList2.setBehaviourName("遗失赔款");
        detailList2.setAvoidPunish("1");
        BigDecimal dueDebt2 = new BigDecimal("150");
        detailList2.setDueDebt("150");
        detailList2.setTitle("java从入门到放弃");
        detailList2.setAuthor("清华大学出版社");
        detailList2.setCallNo("A81/1");
        detailList2.setLoanDate(DateUtil.getTimeNow());
        detailList2.setNormReturnDate(DateUtil.getTimeNow());
        LIST.add(detailList2);

        ChaoxingUserDueDetailResult.DueDetailList detailList1 = new ChaoxingUserDueDetailResult.DueDetailList();
        detailList1.setBehaviourDetailId(StringUtil.generateString(6));
        detailList1.setBehaviourTypeId("3");
        detailList1.setBehaviourName("遗失赔款");
        detailList1.setAvoidPunish("1");
        BigDecimal dueDebt1 = new BigDecimal("100");
        detailList1.setDueDebt(dueDebt1.toString());
        detailList1.setTitle("三分天下");
        detailList1.setAuthor("诸葛亮");
        detailList1.setCallNo("A81/1");
        detailList1.setLoanDate(DateUtil.getTimeNow());
        detailList1.setNormReturnDate(DateUtil.getTimeNow());
        LIST.add(detailList1);
    }

    /**
     * 读者在借信息查询地址
     * @param request 请求
     * @param param 参数
     * @return 返回结果
     */
    @RequestMapping(value = "/fu/loa/lendbook/lending", method = RequestMethod.POST)
    public String lending(HttpServletRequest request, @RequestBody ChaoXingLibraryLendingParam param,
                          @RequestHeader(name = "Authorization") String authorization) {
        System.out.println(param);
        System.out.println(authorization);
        /*Map<String, String> map = HttpServletRequestUtil.getRequestParmeter(request);
        JSONObject jsonObject = new JSONObject(Boolean.TRUE);
        String sign = SignUtil.getSignByMd5(map, KEY);
        if (!sign.equalsIgnoreCase(param.getSign())) {
            jsonObject.put("statusCode", -1);
            jsonObject.put("message", "签名不正确");
            return jsonObject.toString();
        }
        List<JiaofeiQueryDateResponse> list = getJiaofeiQueryDateResponses(param);
        
        jsonObject.put("statusCode", 0);
        jsonObject.put("message", "操作成功");
        jsonObject.put("data", JSONArray.toJSON(list));
        return jsonObject.toJSONString();*/
        String res = "{\n" + "  \"success\": true,\n" + "  \"errorCode\": 0,\n" + "  \"message\": \"成功\",\n" + "  \"data\": [\n" + "    {\n"
                     + "      \"loanId\": 40,\n" + "      \"userId\": 1,\n" + "      \"userLibCode\": \"0000000000\",\n"
                     + "      \"userLibName\": \"测试图书馆\",\n" + "      \"normReturnDate\": \"2018-05-27 08:12:28\",\n" + "      \"renewTimes\": 0,\n"
                     + "      \"recallTimes\": 0,\n" + "      \"loanDate\": \"2018-05-21 08:12:28\",\n" + "      \"locationId\": 3,\n"
                     + "      \"locationName\": \"人文图书馆藏地\",\n" + "      \"itemLibCode\": \"0000000000\",\n" + "      \"itemLibName\": \"测试图书馆\",\n"
                     + "      \"loanDeskId\": 1,\n" + "      \"loanDeskName\": \"默认工作台\",\n" + "      \"attachment\": \"\",\n"
                     + "      \"renewDate\": \"2018-05-21 08:12:28\",\n" + "      \"loanType\": \"2\",\n" + "      \"title\": \"用药咨询\",\n"
                     + "      \"author\": \"郭曼茜主编\",\n" + "      \"publisher\": \"杭州出版社\",\n" + "      \"isbn\": \"7-80633-326-6\",\n"
                     + "      \"isbn10\": \"7530833340\",\n" + "      \"isbn13\": \"9787530833346\",\n" + "      \"publishYear\": \"2002\",\n"
                     + "      \"barcode\": \"12123123\",\n" + "      \"recordId\": \"248\"\n" + "    }\n" + "  ]\n" + "}";
        return res;

    }

    @RequestMapping(value = "/oauth/token", method = RequestMethod.GET)
    public String getToken(String grant_type, String scope) {
        log.info("grant_type:{}, socpe:{}", grant_type, scope);
        String str = " {\n"
                     + "            \"access_token\": \"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJ4Il0sImV4cCI6MTUyNjY1NDA3NywianRpIjoiMGIxYTdmNDMtMmU5Yi00NzAzLWIwZTYtNmJlMGM5ODEwZmI3IiwiY2xpZW50X2lkIjoiYzFjMWE0OTdlNzU3NDJkOTk1YjM1OGQ5Njk2NzcxNDUiLCJncm91cENvZGUiOiIwMDAwMDAwMDAwIiwibWFwcGluZ1BhdGgiOiJuanQifQ.InoBIu9ZvtT_TG6dFEn-bRhAaQ_dv2mAqqLu5eREehC2M4uh-Tpk3z5dAqaCUuLkcJILxLlZ0nI8jmjSRKSEdN2vVNLmIB-LsLXMVhOjcZny1NWge5cGy7yvrJr3dYUnmZwCJFe1XDzsrnzmv8ZLaeHPcXhsjSBMuuNGABbqjXk1FMOUglzQtY_fl03nhMYn_dGushyidS1EFz2IHD_t3VrT5mesa5SeQWZFD6Xc78rV-Jkq7uzjBIb21OaiqR-8x2JvW0MGENlaoKrGX4SrOcWxXFpArG6dOtPWCpF3URPsktn2YCgPHT9RgY8UOOA5DWUtCweF6d_P-OK_d_tExg\",\n"
                     + "                \"token_type\": \"bearer\",\n" + "                \"expires_in\": 43199,\n"
                     + "                \"scope\": \"x\",\n" + "                \"groupCode\": \"0000000000\",\n"
                     + "                \"mappingPath\": \"njt\",\n" + "                \"jti\": \"0b1a7f43-2e9b-4703-b0e6-6be0c9810fb7\"\n"
                     + "        }";
        ChaoXingLibraryTokenResult tokenResult = JSONObject.parseObject(str, ChaoXingLibraryTokenResult.class);
        return str;
    }

    @RequestMapping(value = "/open/user/getUserOverDueDetail", method = RequestMethod.GET)
    public String getUserOverDueDetail(String primaryId) {
        log.info("primaryId:{}", primaryId);
        ChaoxingUserDueDetailResult detailResult = new ChaoxingUserDueDetailResult();
        //detailResult.setDuePay("");
        BigDecimal duePay = BigDecimal.ZERO;
        for (ChaoxingUserDueDetailResult.DueDetailList detailList : LIST) {
            duePay = duePay.add(new BigDecimal(detailList.getDueDebt()));
        }
        detailResult.setDuePay(duePay.toString());
        detailResult.setList(JSONObject.toJSONString(LIST));

        ChaoXingBaseResult baseResult = new ChaoXingBaseResult();
        baseResult.setSuccess(true);
        baseResult.setMessage("操作成功");
        baseResult.setErrCode("0");
        baseResult.setData(JSONObject.toJSONString(detailResult));
        return JSONObject.toJSONString(baseResult);
        //return str;
    }

    /**
     * 读者在借信息查询地址
     * @param request 请求
     * @param param 参数
     * @return 返回结果
     */
    @RequestMapping(value = "/open/userPayAllDebt", method = RequestMethod.POST)
    public String userPayAllDebt(HttpServletRequest request, @RequestBody ChaoXingNoticeParam param,
                                 @RequestHeader(name = "Authorization") String authorization) {
        BigDecimal duePay = BigDecimal.ZERO;
        for (ChaoxingUserDueDetailResult.DueDetailList detailList : LIST) {
            duePay = duePay.add(new BigDecimal(detailList.getDueDebt()));
        }
        if (new BigDecimal(param.getDueDebt()).compareTo(duePay) == 0) {
            ChaoXingBaseResult baseResult = new ChaoXingBaseResult();
            baseResult.setSuccess(true);
            baseResult.setMessage("操作成功");
            baseResult.setErrCode("0");
            LIST.clear();
            return JSONObject.toJSONString(baseResult);
        } else {
            ChaoXingBaseResult baseResult = new ChaoXingBaseResult();
            baseResult.setSuccess(false);
            baseResult.setMessage("金额不一致");
            baseResult.setErrCode("500");
            return JSONObject.toJSONString(baseResult);
        }

    }
}
