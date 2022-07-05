package com.electric.controller.net;

import com.alibaba.fastjson.JSONObject;
import com.electric.constant.StringConstant;
import com.electric.param.net.NetRequestParam;
import com.electric.response.net.cityhot.CityHotV5AccountInfoResponse;
import com.electric.util.Base64Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 城市热点网费
 *
 * @Author Administrator
 * @Date 2021年10月18日
 */
@RestController
@RequestMapping("/DrcomSrv")
public class NetCityHotController {

    private static final Logger LOG = LoggerFactory.getLogger(NetCityHotController.class);

    /**
     * 城市热点接口
     *
     * @param request
     * @param business
     * @return
     */
    @RequestMapping(value = "/DrcomServlet", method = RequestMethod.POST)
    public String DrcomServlet(HttpServletRequest request, String business) {
        printParameters(request);
        String businessdecode = Base64Util.decodeString(business);
        LOG.info("接收参数：{}", businessdecode);
        if (businessdecode.startsWith("091")) {
//            String result = "E00\t1\t2017-11-20 09:39:30\t资费变更\t0\t0\t0\t0\t2017-11-20 00:00:00\t张婷\t4\t1\t0100000000000000000000000000000000000000000000000000000000000000\t0";
            StringBuilder sb = new StringBuilder("E00").append(StringConstant.T);
            sb.append("1").append(StringConstant.T);
            sb.append("2017-11-20 09:39:30").append(StringConstant.T);
            sb.append("资费变更").append(StringConstant.T);
            sb.append("0").append(StringConstant.T);
            sb.append("0").append(StringConstant.T);
            sb.append("0").append(StringConstant.T);
            sb.append("0").append(StringConstant.T);
            sb.append("2017-11-20 00:00:00").append(StringConstant.T);
            sb.append("张婷").append(StringConstant.T);
            sb.append("4").append(StringConstant.T);
            sb.append("1").append(StringConstant.T);
            sb.append("0100000000000000000000000000000000000000000000000000000000000000").append(StringConstant.T);

            LOG.info("查询账号基本信息返回：{}", sb.toString());
            return sb.toString();
        } else if (businessdecode.startsWith("010")) {
            String result = "E00";
            LOG.info("充值接口返回：{}", result);
            return result;
        } else if (businessdecode.startsWith("S92")) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", "E00");
            jsonObject.put("list", null);
            LOG.info("查询订单充值结果返回：{}", jsonObject.toString());
            return jsonObject.toString();
        }
        return business;
    }

    /**
     * 城市热点V5网费
     *
     * @param request
     * @param business
     * @return
     */
    @RequestMapping(value = "/DrcomService", method = RequestMethod.POST)
    public String DrcomService(HttpServletRequest request, String business) {
        printParameters(request);
        String businessdecode = Base64Util.decodeString(business);
        LOG.info("接收参数：{}", businessdecode);
//        JSONObject jsonObject = JSONObject.parseObject(businessdecode);
        NetRequestParam param = JSONObject.parseObject(businessdecode, NetRequestParam.class);
        if (param.getCode().equals("S01")) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", "E00");

            List<CityHotV5AccountInfoResponse> list = new ArrayList<>();

            for (int i = 0; i <= 20; i++) {
                CityHotV5AccountInfoResponse response1 = new CityHotV5AccountInfoResponse();
                response1.setAccount(param.getAccount());
                response1.setBalance(220.0);
                response1.setUser_state(1);
                response1.setPackage_group_id(i);
                list.add(response1);
            }
            jsonObject.put("list", list);
            return jsonObject.toJSONString();
        }

        return business;
    }

    /**
     * 打印参数
     *
     * @param request
     */
    private void printParameters(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();

            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length > 0) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }

        //        String business = map.containsKey("business") ? map.get("business").toString() : null;

        Set<Map.Entry<String, Object>> set = map.entrySet();
        System.out.println("==============================================================");
        for (Map.Entry entry : set) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}
