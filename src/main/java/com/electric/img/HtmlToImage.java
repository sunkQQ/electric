package com.electric.img;

import java.util.HashMap;
import java.util.Map;

/**
 * HTML转图片
 *
 * @author sunk
 * @date 2024/02/20
 */
public class HtmlToImage {
    public static void main(String[] args) throws Exception {
        String StrTemplate = "<html>\n" + "<head>\n" + "<title></title>\n" + "</head>\n" + "<body>\n" + "<form>\n"
                             + "姓名：<input type=\"text\" value=\"\" name=\"user\"><br><br>\n"
                             + "密码：<input type=\"password\" value=\"\" name=\"pass\"><br><br>\n" + "性别：<tr><td>\n"
                             + "            <input type=\"radio\" name=\"sex\" value=\"1\">男\n"
                             + "            <input type=\"radio\" name=\"sex\" value=\"1\">女\n" + "                    </td></tr><br><br>\n"
                             + "   喜爱的运动：<br><input type=\"checkbox\" name=\"baskball\" value=\"1\">篮球\n"
                             + "                            <input type=\"checkbox\" name=\"football\" value=\"1\">足球\n"
                             + "                <input type=\"checkbox\" name=\"baseball\" value=\"1\">棒球\n"
                             + "                    <input type=\"checkbox\" name=\"volleyball\" value=\"1\">排球 <br><br>\n"
                             + "                <input type=\"submit\"><br><br>\n" + "                            <!-- 提交 -->\n" + "   地址：<tr><td>\n"
                             + "               <select multiple=\"multiple\">\n" + "                      <option>北京</option>\n"
                             + "        <option>上海</option>\n" + "        <option>天津</option>\n" + "        <option>深圳</option>\n"
                             + "        <option>秦皇岛</option>\n" + "             </select>\n" + "             </td></tr><br><br>\n"
                             + "   个人简介：<br><textarea name=\"info\" cols=\"30\" rows=\"10\">填写信息</textarea><br><br>\n" + "     <fieldset>\n"
                             + "     <legend>健康信息</legend>\n" + "     身高：<input type=\"text\"> 体重：<input type=\"text\">\n" + "     </fieldset>\n"
                             + "   <input type=\"button\" value=\"提交\">\n" + "   <input type=\"button\" value=\"重置\">\n" + "</form>\n" + "</body>\n"
                             + "</html>"; // 测试模板数据（一般存储在数据库中）
        Map<String, Object> map = new HashMap<String, Object>(); // map，需要动态填充的数据
        map.put("name", "张三");//需要赋值的属性   例： ${name}
        FreemarkerUtils.turnImage(StrTemplate, map);
    }

}
