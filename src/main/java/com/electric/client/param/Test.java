package com.electric.client.param;

import com.alibaba.fastjson.JSONObject;
import com.electric.param.ElectricQueryRecordParam;

/**
 * @author sunk
 * @date 2023/11/28
 */
public class Test {

    public static void main(String[] args) {
        ElectricQueryRecordParam param = new ElectricQueryRecordParam();
        param.setBuildingCode("01");
        System.out.println(JSONObject.toJSONString(param));
    }

}
