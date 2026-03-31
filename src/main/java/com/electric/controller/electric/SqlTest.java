package com.electric.controller.electric;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.electric.model.response.SelectResult;
import com.electric.util.JdbcUtil;

/**
 *
 *
 * @author sunk
 * @date 2026/3/31
 */
public class SqlTest {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://192.168.68.10:6446/yunma_center?useSSL=true&requireSSL=true&verifyServerCertificate=false&serverTimezone=Asia/Shanghai&cachePrepStmts=true&useServerPrepStmts=true&rewriteBatchedStatements=true&connectTimeout=3000&socketTimeout=30000&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
        List<Map<String, Object>> list = JdbcUtil.executeQuery(url, "yunma_center", "P%rXjqocyUf1B0PDy8",
            "select * from base_area where delete_flag = 1");
        System.out.println(list.size());
        List<SelectResult> results = new ArrayList<>();
        for (Map<String, Object> map : list) {
            SelectResult result = new SelectResult();
            result.setId(map.get("area_code").toString());
            result.setText(map.get("area_name").toString());
            result.setStatus(Integer.valueOf(map.get("area_level").toString()));
            result.setParentId(map.get("parent_code").toString());
            results.add(result);
        }

        List<SelectResult> topResults = results.stream().filter(s -> s.getParentId().equals("0")).collect(Collectors.toList());
        for (SelectResult guo : topResults) {
            JSONObject guoJson = new JSONObject();
            guoJson.put("code", guo.getId());
            guoJson.put("name", guo.getText());
            List<SelectResult> quyuList = results.stream().filter(s -> s.getParentId().equals(guo.getId())).collect(Collectors.toList());
            JSONArray quyuJsonArray = new JSONArray();
            for (SelectResult quyu : quyuList) {
                JSONObject quyuJson = new JSONObject();
                quyuJson.put("code", quyu.getId());
                quyuJson.put("name", quyu.getText());
                JSONArray shengfenJsonArray = new JSONArray();
                List<SelectResult> shengfenList = results.stream().filter(s -> s.getParentId().equals(quyu.getId())).collect(Collectors.toList());
                for (SelectResult shengfen : shengfenList) {
                    JSONObject shengfenJson = new JSONObject();
                    shengfenJson.put("code", shengfen.getId());
                    shengfenJson.put("name", shengfen.getText());

                    List<SelectResult> quList = results.stream().filter(s -> s.getParentId().equals(shengfen.getId())).collect(Collectors.toList());
                    JSONArray quJsonArray = new JSONArray();
                    for (SelectResult qu : quList) {
                        JSONObject quJson = new JSONObject();
                        quJson.put("code", qu.getId());
                        quJson.put("name", qu.getText());
                        List<SelectResult> xianList = results.stream().filter(s -> s.getParentId().equals(qu.getId())).collect(Collectors.toList());
                        JSONArray xianJsonArray = new JSONArray();
                        for (SelectResult xian : xianList) {
                            JSONObject xianJson = new JSONObject();
                            xianJson.put("code", xian.getId());
                            xianJson.put("name", xian.getText());
                            xianJsonArray.add(xianJson);
                        }
                        quJson.put("child", xianJsonArray);
                        quJsonArray.add(quJson);
                    }
                    shengfenJson.put("child", quJsonArray);
                    shengfenJsonArray.add(shengfenJson);
                }
                quyuJson.put("child", shengfenJsonArray);
                quyuJsonArray.add(quyuJson);
            }
            guoJson.put("child", quyuJsonArray);
            System.out.println(guoJson);
        }
    }
}
