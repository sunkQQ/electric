package com.electric.util;

import cn.hutool.core.util.RandomUtil;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 * 双色球
 *
 * @author sunk
 * @date 2024/01/24
 */
public class DoubleColorBall {

    public static void main(String[] args) {
        getDoubleColorBallNumber(100);
    }

    public static void getDoubleColorBallNumber(int num) {
        System.out.println("随机生成" + num + "注双色球号码为：");
        String resultNumber = "";
        for (int i = 0; i < num; i++) {
            System.out.println("【" + (i + 1) + "】" + resultNumber + getDoubleColorBallNumber());
        }

    }

    /**
     * 获取单注双色球号码
     * @return 单注双色球号码
     */
    public static String getDoubleColorBallNumber() {
        String resultNumber = "";
        for (int i = 0; i < 6; i++) {
            String ballNumber = RandomUtil.randomEle(getRedBalls()) + "\t";
            resultNumber = resultNumber + ballNumber;
        }
        return resultNumber + "|\t" + RandomUtil.randomEle(getBlueBalls());
    }

    /**
     * 获取红色球球号集合b
     * @return 红色球球号集合
     */
    public static List<String> getRedBalls() {
        return getBalls(33);
    }

    /**
     * 获取蓝色球球号集合
     * @return 蓝色球球号集合
     */
    public static List<String> getBlueBalls() {
        return getBalls(16);
    }

    /**
     * 获取球色集合 v
     * @param num
     * @return  sd
     */
    public static List<String> getBalls(int num) {
        List<String> redBalls = Lists.newArrayList();
        for (int i = 1; i <= num; i++) {
            int length = String.valueOf(num).length();
            String str = String.format("%0" + length + "d", i);
            //System.out.println(str);
            redBalls.add(str);
        }
        return redBalls;
    }
}
