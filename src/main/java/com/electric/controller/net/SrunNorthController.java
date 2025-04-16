package com.electric.controller.net;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

/**
 * 深澜北向网费
 *
 * @author sunk
 * @date 2025/2/25
 */
@RestController
@RequestMapping("/api/v1")
public class SrunNorthController {

    //深澜网费北向接口获取令牌接口返回结果：{"code":0,"message":"ok","version":"v1.6.0","data":{"access_token":"CGp3GhWNWHrjB7gRvuMi6BYI47LtUFZr"}}
    //深澜网费北向接口请求查询已购产品接口，账号10757232185，参数：{access_token=CGp3GhWNWHrjB7gRvuMi6BYI47LtUFZr, user_name=10757232185}

    //   深澜网费北向接口获取令牌接口返回结果：{"code":0,"message":"ok","version":"v1.6.0","data":{"access_token":"CGp3GhWNWHrjB7gRvuMi6BYI47LtUFZr"}}
    //深澜网费北向接口请求查询用户详情接口，账号10757232185，参数：{access_token=CGp3GhWNWHrjB7gRvuMi6BYI47LtUFZr, user_name=10757232185}
    //深澜网费北向接口请求查询用户详情接口返回，账号：10757232185，返回结果：{"code":0,"message":"ok","version":"v1.6.0","data":{"user_id":77763,"user_name":"10757232185","user_real_name":"关**","group_id":12,"region_id":0,"user_create_time":1694011040,"user_update_time":1739878741,"user_expire_time":1785513600,"user_status":0,"balance":"0.00","mgr_name_create":"yongfei","mgr_name_update":"wanggaokai","user_start_time":0,"user_stop_time":0,"user_allow_chgpass":1,"cert_type":"0","cert_num":"150404199612091419","phone":"","email":"","address":"","user_type":"材料工程","can_create_visitor":"0","create_visitor_num":"0","user_available":"0","question1":null,"answer1":null,"question2":null,"answer2":null,"question3":null,"answer3":null,"school_type":"","last_online":1740403706,"last_offline":1740420530,"pay_type":"0","user_address":"","user_student_card":"","user_description":"硕士研究生","user_dept":"化学化工学院","max_online_num":"0","no_mac_auth":"0"}}
    //深澜网费北向接口请求查询已购产品接口，账号10757232185，参数：{access_token=CGp3GhWNWHrjB7gRvuMi6BYI47LtUFZr, user_name=10757232185}
    //深澜网费北向接口请求查询已购产品接口返回，账号：10757232185，返回结果：{"code":0,"message":"ok","version":"v1.6.0","data":[{"user_id":77763,"products_id":64,"products_name":"学生公寓二（电信）","billing_name":"免费计费策略","control_name":"可欠费区域（电信）---校园网无线（电信）---学生公寓二-控制策略（电信）---学生公寓无线计费网络（电信1）（电信）---东扩区宿舍有线网络（研究生）（电信）","condition":"1","checkout_date":"2025-03-01","sum_bytes":38983341253,"sum_seconds":249657,"sum_times":23,"package_name":"","user_charge":0,"user_balance":40,"checkout_mode":"1","checkout_amount":0,"allow_payment":"yes","mobile_phone":"","expire_time":0,"user_available":null},{"user_id":77763,"products_id":127,"products_name":"学生公寓二 （移动）","billing_name":"免费计费策略","control_name":"东扩区宿舍有线网络(研究生) （移动）---学生公寓二-控制策略（移动）---学生公寓无线计费网络（移动）---可欠费区域（移动）---校园网无线（移动）","condition":"1","checkout_date":"2025-03-01","sum_bytes":0,"sum_seconds":0,"sum_times":0,"package_name":"","user_charge":0,"user_balance":0,"checkout_mode":"1","checkout_amount":0,"allow_payment":"yes","mobile_phone":"","expire_time":0,"user_available":"0"},{"user_id":77763,"products_id":130,"products_name":"学生公寓二 （联通）","billing_name":"免费计费策略","control_name":"东扩区宿舍有线网络(研究生) （联通）---学生公寓二-控制策略 （联通）---学生公寓无线计费网络 （联通）---可欠费区域 （联通）---校园网无线（联通）","condition":"1","checkout_date":"2025-03-01","sum_bytes":0,"sum_seconds":0,"sum_times":0,"package_name":"","user_charge":0,"user_balance":0,"checkout_mode":"1","checkout_amount":0,"allow_payment":"yes","mobile_phone":"","expire_time":0,"user_available":"0"}]}
    @RequestMapping(value = "/auth/get-access-token", method = RequestMethod.GET)
    public String getAccessToken() {
        JSONObject json = new JSONObject();
        json.put("code", 0);
        json.put("message", "ok");
        json.put("version", "v1.6.0");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("access_token", "CGp3GhWNWHrjB7gRvuMi6BYI47LtUFZr");
        json.put("data", jsonObject);
        return json.toString();
    }

    @RequestMapping(value = "/user/view", method = RequestMethod.GET)
    public String userView(HttpServletRequest request, String user_name) {
        //{"code":0,"message":"ok","version":"v1.6.0","data":{"user_id":77763,"user_name":"10757232185","user_real_name":"关**","group_id":12,"region_id":0,"user_create_time":1694011040,"user_update_time":1739878741,"user_expire_time":1785513600,"user_status":0,"balance":"0.00","mgr_name_create":"yongfei","mgr_name_update":"wanggaokai","user_start_time":0,"user_stop_time":0,"user_allow_chgpass":1,"cert_type":"0","cert_num":"150404199612091419","phone":"","email":"","address":"","user_type":"材料工程","can_create_visitor":"0","create_visitor_num":"0","user_available":"0","question1":null,"answer1":null,"question2":null,"answer2":null,"question3":null,"answer3":null,"school_type":"","last_online":1740403706,"last_offline":1740420530,"pay_type":"0","user_address":"","user_student_card":"","user_description":"硕士研究生","user_dept":"化学化工学院","max_online_num":"0","no_mac_auth":"0"}}

        JSONObject data = new JSONObject();
        data.put("user_id", 77763);
        data.put("user_name", user_name);
        data.put("user_real_name", "关**");
        data.put("group_id", 12);
        data.put("region_id", 0);
        data.put("user_create_time", 1694011040);
        data.put("user_update_time", 1739878741);
        data.put("user_expire_time", 1785513600);
        data.put("user_status", 0);
        data.put("balance", "0.00");
        data.put("mgr_name_create", "yongfei");
        data.put("mgr_name_update", "wanggaokai");
        data.put("user_start_time", 0);
        data.put("user_stop_time", 0);
        data.put("user_allow_chgpass", 1);
        data.put("cert_type", "0");
        data.put("cert_num", "150404199612091419");
        data.put("phone", "");
        data.put("email", "");
        data.put("address", "");
        data.put("user_type", "材料工程");
        data.put("can_create_visitor", "0");
        data.put("create_visitor_num", "0");
        data.put("user_available", "0");
        data.put("question1", null);
        data.put("answer1", null);
        data.put("question2", null);
        data.put("answer2", null);
        data.put("question3", null);
        data.put("answer3", null);
        data.put("school_type", "");
        data.put("last_online", 1740403706);
        data.put("last_offline", 1740420530);
        data.put("pay_type", "0");
        data.put("user_address", "");
        data.put("user_student_card", "");
        data.put("user_description", "硕士研究生");
        data.put("user_dept", "化学化工学院");
        data.put("max_online_num", "0");
        data.put("no_mac_auth", "0");

        JSONObject response = new JSONObject();
        response.put("code", 0);
        response.put("message", "ok");
        response.put("version", "v1.6.0");
        response.put("data", data);

        return response.toString();
    }

    @RequestMapping(value = "/package/users-packages", method = RequestMethod.GET)
    public String usersPackages(HttpServletRequest request, String user_name) {
        //{"code":0,"message":"ok","version":"v1.6.0","data":[{"user_id":77763,
        //        "products_id":64,"products_name":"学生公寓二（电信）","billing_name":"免费计费策略",
        //        "control_name":"可欠费区域（电信）---校园网无线（电信）---学生公寓二-控制策略（电信）---学生公寓无线计费网络（电信1）（电信）---东扩区宿舍有线网络（研究生）（电信）",
        //        "condition":"1","checkout_date":"2025-03-01","sum_bytes":38983341253,"sum_seconds":249657,"sum_times":23,"package_name":"",
        //        "user_charge":0,"user_balance":40,"checkout_mode":"1","checkout_amount":0,"allow_payment":"yes","mobile_phone":"","expire_time":0,
        //        "user_available":null},{"user_id":77763,"products_id":127,"products_name":"学生公寓二 （移动）","billing_name":"免费计费策略",
        //        "control_name":"东扩区宿舍有线网络(研究生) （移动）---学生公寓二-控制策略（移动）---学生公寓无线计费网络（移动）---可欠费区域（移动）---校园网无线（移动）",
        //        "condition":"1","checkout_date":"2025-03-01","sum_bytes":0,"sum_seconds":0,"sum_times":0,"package_name":"","user_charge":0,"user_balance":0,
        //        "checkout_mode":"1","checkout_amount":0,"allow_payment":"yes","mobile_phone":"","expire_time":0,"user_available":"0"},
        //        {"user_id":77763,"products_id":130,"products_name":"学生公寓二 （联通）","billing_name":"免费计费策略","control_name":"东扩区宿舍有线网络(研究生) （联通）---学生公寓二-控制策略 （联通）---学生公寓无线计费网络 （联通）---可欠费区域 （联通）---校园网无线（联通）","condition":"1","checkout_date":"2025-03-01","sum_bytes":0,"sum_seconds":0,"sum_times":0,"package_name":"","user_charge":0,"user_balance":0,"checkout_mode":"1","checkout_amount":0,"allow_payment":"yes","mobile_phone":"","expire_time":0,"user_available":"0"}]}

        JSONArray products = new JSONArray();

        JSONObject product1 = new JSONObject();
        product1.put("user_id", 77763);
        product1.put("products_id", 64);
        product1.put("products_name", "学生公寓二（电信）");
        product1.put("billing_name", "免费计费策略");
        product1.put("control_name", "可欠费区域（电信）---校园网无线（电信）---学生公寓二-控制策略（电信）---学生公寓无线计费网络（电信1）（电信）---东扩区宿舍有线网络（研究生）（电信）");
        product1.put("condition", "1");
        product1.put("checkout_date", "2025-03-01");
        product1.put("sum_bytes", 38983341253L);
        product1.put("sum_seconds", 249657L);
        product1.put("sum_times", 23);
        product1.put("package_name", "");
        product1.put("user_charge", 0);
        product1.put("user_balance", 40);
        product1.put("checkout_mode", "1");
        product1.put("checkout_amount", 0);
        product1.put("allow_payment", "yes");
        product1.put("mobile_phone", "");
        product1.put("expire_time", 0);
        product1.put("user_available", null);

        JSONObject product2 = new JSONObject();
        product2.put("user_id", 77763);
        product2.put("products_id", 127);
        product2.put("products_name", "学生公寓二 （移动）");
        product2.put("billing_name", "免费计费策略");
        product2.put("control_name", "东扩区宿舍有线网络(研究生) （移动）---学生公寓二-控制策略（移动）---学生公寓无线计费网络（移动）---可欠费区域（移动）---校园网无线（移动）");
        product2.put("condition", "1");
        product2.put("checkout_date", "2025-03-01");
        product2.put("sum_bytes", 0L);
        product2.put("sum_seconds", 0L);
        product2.put("sum_times", 0);
        product2.put("package_name", "");
        product2.put("user_charge", 0);
        product2.put("user_balance", 0);
        product2.put("checkout_mode", "1");
        product2.put("checkout_amount", 0);
        product2.put("allow_payment", "yes");
        product2.put("mobile_phone", "");
        product2.put("expire_time", 0);
        product2.put("user_available", "0");

        JSONObject product3 = new JSONObject();
        product3.put("user_id", 77763);
        product3.put("products_id", 130);
        product3.put("products_name", "学生公寓二 （联通）");
        product3.put("billing_name", "免费计费策略");
        product3.put("control_name", "东扩区宿舍有线网络(研究生) （联通）---学生公寓二-控制策略 （联通）---学生公寓无线计费网络 （联通）---可欠费区域 （联通）---校园网无线（联通）");
        product3.put("condition", "1");
        product3.put("checkout_date", "2025-03-01");
        product3.put("sum_bytes", 0L);
        product3.put("sum_seconds", 0L);
        product3.put("sum_times", 0);
        product3.put("package_name", "");
        product3.put("user_charge", 0);
        product3.put("user_balance", 0);
        product3.put("checkout_mode", "1");
        product3.put("checkout_amount", 0);
        product3.put("allow_payment", "yes");
        product3.put("mobile_phone", "");
        product3.put("expire_time", 0);
        product3.put("user_available", "0");

        products.add(product1);
        products.add(product2);
        products.add(product3);

        JSONObject response = new JSONObject();
        response.put("code", 0);
        response.put("message", "ok");
        response.put("version", "v1.6.0");
        response.put("data", products);

        return response.toString();
    }

    @RequestMapping(value = "/product/recharge", method = RequestMethod.POST)
    public String recharge(HttpServletRequest request) {
        //{"code":0,"message":"ok","version":"v1.6.0"}

        JSONObject response = new JSONObject();
        response.put("code", 0);
        response.put("message", "ok");
        response.put("version", "v1.6.0");
        return response.toString();
    }
}
