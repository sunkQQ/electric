package com.electric.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;

/**
 * @author sunk
 * @since 2026/1/28
 */
@RestController
@RequestMapping("/swagger")
public class SwaagerController {

    @PostMapping(value = "/getDetail")
    public String getParamHtml(HttpServletRequest request, String interfaceDesc, String route, String name) {

        String category = "/route/stl/account/addAutoWithdrawConfig,/route/stl/account/addSignAgreement,/route/stl/account/applyVerify,/route/stl/account/bindingApply,/route/stl/account/getAccountBillCount,/route/stl/account/getAccountRecharge,/route/stl/account/getAccountWithdraw,/route/stl/account/getAutoWithdrawConfigDetail,/route/stl/account/keepresponse/exportSchoolBillList,/route/stl/account/queryAccountConfig,/route/stl/account/queryAccountList,/route/stl/account/queryBillDetail,/route/stl/account/queryBillList,/route/stl/account/queryBindingDetail,/route/stl/account/queryBindingList,/route/stl/account/recharge,/route/stl/account/settingDefaultCard,/route/stl/account/withdraw,/route/stl/bank/list,/route/stl/certType/list,/route/stl/common/data/getRepeatDataList,/route/stl/common/data/getRepeatTypeList,/route/stl/order/getOrderBusinessTypeList,/route/stl/payment/authorize/getSchoolBusinessAuthorizeInfoList,/route/stl/payment/channel/getPaymentChannelSelect";
        Map<String, String> map = new HashMap<>();
        List<String> list = Arrays.stream(category.split(",")).distinct().collect(Collectors.toList());
        for (String s : list) {
            map.put(s, s);
        }

        JSONObject parseObject = JSON.parseObject(interfaceDesc);

        String paths = parseObject.getString("paths");
        Map<String, JSONObject> parseObject2 = JSON.parseObject(paths, new TypeReference<Map<String, JSONObject>>() {
        });
        List<String> str = new ArrayList<>();
        for (Map.Entry<String, JSONObject> set : parseObject2.entrySet()) {
            //数据库中没有该uri,无需补全

            if (!list.contains(set.getKey())) {
                continue;
            }

            JSONObject value = set.getValue();
            int i = 0;
            for (String keySet : value.keySet()) {
                //目前暂不区分post get，所以取第一条即可
                if (i > 0) {
                    break;
                }
                JSONObject jsonObject = (JSONObject) value.get(keySet);
                JSONArray jsonArray = jsonObject.getJSONArray("tags");
                if (jsonArray.size() == 1) {
                    str.add(route + set.getKey() + "\t" + name + jsonArray.getString(0) + jsonObject.getString("summary"));
                } else if (jsonArray.size() == 2) {
                    if (StringUtils.equalsAny(jsonArray.getString(0), "INNER", "OUTER")) {
                        str.add(route + set.getKey() + "\t" + name + jsonArray.getString(1) + jsonObject.getString("summary"));
                    } else {
                        str.add(route + set.getKey() + "\t" + name + jsonArray.getString(0) + jsonObject.getString("summary"));
                    }
                }
                map.remove(set.getKey());
                //System.out.println(set.getKey() + "\t" + jsonObject.getString("summary"));
                //newDO.setInterfaceDesc(jsonObject.getString("summary"));

                i++;
            }
        }
        for (Map.Entry<String, String> urlStr : map.entrySet()) {
            str.add(route + urlStr.getKey());
        }
        str.stream().distinct().sorted().forEach(System.out::println);
        // System.out.println("test");
        return "index2";
    }
}
