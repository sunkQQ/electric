package com.electric.controller.jiaofei;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.electric.constant.Numbers;
import com.electric.param.jiaofei.tiancai.JiaofeiTianCaiAbcJsonParam;
import com.electric.param.jiaofei.tiancai.JiaofeiTianCaiBaseParam;
import com.electric.param.jiaofei.tiancai.JiaofeiTianCaiNoticeParam;
import com.electric.response.jiaofei.tiancai.JiaofeiTianCaiBaseResponse;
import com.electric.response.jiaofei.tiancai.JiaofeiTianCaiPaymentResponse;
import com.electric.response.jiaofei.tiancai.JiaofeiTianCaiUserInfoResponse;
import com.electric.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 天大天财缴费接口
 *
 * @author sunk
 * @date 2023/07/21
 */
@RestController
@RequestMapping("/appFace")
public class JiaofeiTianCaiController {

    // 学生基本信息查询
    private final static String                              QUERY_USER_INFO = "ABC0001";

    // 学生缴费信息查询
    private final static String                              QUERY_PAYMENT   = "ABC0002";

    // 学生订单缴费确认
    private final static String                              NOTICE          = "ABC0003";

    private final static List<JiaofeiTianCaiPaymentResponse> PAYMENT_LIST    = new ArrayList<>();

    static {
        JiaofeiTianCaiPaymentResponse payment1 = new JiaofeiTianCaiPaymentResponse();
        payment1.setSfqjdm("202308");
        payment1.setSfqjmc("2023-2024");
        payment1.setSfxmdm("02");
        payment1.setSfxmmc("住宿费");
        payment1.setJyye(new BigDecimal(10));
        PAYMENT_LIST.add(payment1);

        JiaofeiTianCaiPaymentResponse payment2 = new JiaofeiTianCaiPaymentResponse();
        payment2.setSfqjdm("202308");
        payment2.setSfqjmc("2023-2024");
        payment2.setSfxmdm("01");
        payment2.setSfxmmc("学费");
        payment2.setJyye(new BigDecimal(10));
        PAYMENT_LIST.add(payment2);
    }

    /**
     * 查询接口
     * @param param 参数
     * @return 返回结果
     */
    @RequestMapping(value = "/BasePage.aspx", method = RequestMethod.POST)
    public String query(JiaofeiTianCaiBaseParam param) {
        JiaofeiTianCaiAbcJsonParam abcJson = JSONObject.parseObject(param.getAbcJson(), JiaofeiTianCaiAbcJsonParam.class);
        if (StringUtils.isBlank(abcJson.getSfzh())) {
            return null;
        }
        switch (abcJson.getAbcCode()) {
            case QUERY_USER_INFO:
                JiaofeiTianCaiUserInfoResponse userInfo = new JiaofeiTianCaiUserInfoResponse();
                userInfo.setXm(abcJson.getXm());
                userInfo.setXh(abcJson.getXh());
                userInfo.setBjmc("测试班级");
                userInfo.setAbcCode(QUERY_USER_INFO);
                userInfo.setRxnd("2023");
                userInfo.setZjh(abcJson.getSfzh());
                userInfo.setRtnCode("00");
                userInfo.setSign(StringUtil.newGuid20());
                return JSONObject.toJSONString(userInfo);
            case QUERY_PAYMENT: {
                JiaofeiTianCaiBaseResponse base = new JiaofeiTianCaiBaseResponse();

                base.setRtnCode("00");
                base.setRtnMsg("成功");
                base.setAbcCode(QUERY_PAYMENT);
                base.setZje(PAYMENT_LIST.stream().map(JiaofeiTianCaiPaymentResponse::getJyye).reduce(BigDecimal::add).toString());
                String jsonStr = JSONObject.toJSONString(
                    PAYMENT_LIST.stream().filter(p -> p.getJyye().compareTo(BigDecimal.ZERO) > Numbers.INT_0).collect(Collectors.toList()));
                base.setDetail(jsonStr);
                return JSONObject.toJSONString(base);
            }
            case NOTICE: {
                JiaofeiTianCaiNoticeParam noticeParam = JSONObject.parseObject(param.getAbcJson(), JiaofeiTianCaiNoticeParam.class);
                List<JiaofeiTianCaiNoticeParam.Detail> detailList = JSONArray.parseArray(noticeParam.getDetail(),
                    JiaofeiTianCaiNoticeParam.Detail.class);
                for (JiaofeiTianCaiNoticeParam.Detail detail : detailList) {
                    String[] str = detail.getJfbh().split("@");
                    for (JiaofeiTianCaiPaymentResponse payment : PAYMENT_LIST) {
                        if (payment.getSfqjdm().equals(str[0]) && payment.getSfxmdm().equals(str[1])) {
                            payment.setJyye(payment.getJyye().subtract(new BigDecimal(detail.getJe())));
                        }
                    }
                }
                JiaofeiTianCaiBaseResponse base = new JiaofeiTianCaiBaseResponse();

                base.setRtnCode("00");
                base.setRtnMsg("成功");
                base.setAbcCode(NOTICE);
                return JSONObject.toJSONString(base);
            }
            default:
                return null;
        }
    }
}
