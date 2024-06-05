package com.electric.util;

import java.util.Calendar;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 *
 * @author sunk
 * @date 2022/08/09
 */
public class StringUtil {

    public static final String   ALL_CHAR    = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String[] STR_ARR36   = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
                                                 "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
    public static final String   LETTER_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String   NUMBER_CHAR = "0123456789";

    /**
     * 生成20位随机数
     */
    public static String newGuid20() {
        String str1 = "";

        Calendar cal = Calendar.getInstance();
        int yy = cal.get(Calendar.YEAR);
        int mm = cal.get(Calendar.MONTH) + 1;
        int dd = cal.get(Calendar.DAY_OF_MONTH);
        yy = (yy % 100) % 36;
        int hh = 100 + cal.get(Calendar.HOUR);
        ;
        int mins = 100 + cal.get(Calendar.MINUTE);
        int sec = 100 + cal.get(Calendar.SECOND);
        String hhstr = ("" + hh).substring(1);
        String minsStr = ("" + mins).substring(1);
        String secStr = ("" + sec).substring(1);

        str1 = STR_ARR36[yy] + STR_ARR36[mm] + STR_ARR36[dd] + hhstr + minsStr + secStr;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            int k = (int) (Math.random() * 35);
            sb.append(STR_ARR36[k]);
        }
        str1 = str1 + sb.toString();
        return str1;
    }

    /**
     * 返回一个定长的随机字符串(包含大小写字母、数字)
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length())));
        }

        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯字母字符串(只包含大小写字母)
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateMixString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(LETTER_CHAR.charAt(random.nextInt(LETTER_CHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯数字
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateMixNum(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(NUMBER_CHAR.charAt(random.nextInt(NUMBER_CHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯大写字母字符串(只包含大小写字母)
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateLowerString(int length) {
        return generateMixString(length).toLowerCase();
    }

    /**
     * 返回一个定长的随机纯小写字母字符串(只包含大小写字母)
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateUpperString(int length) {
        return generateMixString(length).toUpperCase();
    }

    /**
     * 生成一个定长的纯0字符串
     * @param length 字符串长度
     * @return 纯0字符串
     */
    public static String generateZeroString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    /**
     * 根据数字生成一个定长的字符串，长度不够前面补0
     * @param num 数字
     * @param fixdlenth 字符串长度
     * @return 定长的字符串
     */
    public static String toFixdLengthString(int num, int fixdlenth) {
        StringBuilder sb = new StringBuilder();
        String strNum = String.valueOf(num);
        if (fixdlenth - strNum.length() >= 0) {
            sb.append(generateZeroString(fixdlenth - strNum.length()));
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常!");
        }
        sb.append(strNum);
        return sb.toString();
    }

    /**
     * 生产一个指定长度的随机字符串（字母和数字相间隔）
     *
     * @param length 长度
     * @return 定长的字符串
     */
    public static String getCharacterNumber(int length) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 字符串
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char) (choice + random.nextInt(26)));
                // 数字
            } else {
                val.append(random.nextInt(10));
            }
        }
        return val.toString();
    }

    /**
     * 获取首字母小写
     *
     * @param name
     * @return
     * @create  2020年6月24日 下午3:18:19 luochao
     * @history
     */
    public static String getFirstCharLower(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        return name.substring(0, 1).toLowerCase().concat(name.substring(1));
    }

    public static void main(String[] args) {
        System.out.println(newGuid20());
    }
}
