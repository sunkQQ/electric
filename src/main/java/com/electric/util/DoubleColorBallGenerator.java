package com.electric.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * 双色球
 *
 * @author sunk
 * @since 2026/2/10
 */

public class DoubleColorBallGenerator {

    /**
     * 生成一注双色球号码
     * @return Map<String, List<Integer>> 包含红球和蓝球的Map
     */
    public static Map<String, List<Integer>> generateSingle() {
        Map<String, List<Integer>> result = new HashMap<>();

        // 生成红球（6个，范围1-33，不重复）
        List<Integer> redBalls = new ArrayList<>();
        Random random = new Random();

        while (redBalls.size() < 6) {
            int num = random.nextInt(33) + 1; // 1-33
            if (!redBalls.contains(num)) {
                redBalls.add(num);
            }
        }
        // 红球排序（双色球开奖红球从小到大排列）
        Collections.sort(redBalls);

        // 生成蓝球（1个，范围1-16）
        int blueBall = random.nextInt(16) + 1; // 1-16
        List<Integer> blueBalls = new ArrayList<>();
        blueBalls.add(blueBall);

        result.put("red", redBalls);
        result.put("blue", blueBalls);

        return result;
    }

    /**
     * 生成多注双色球号码
     * @param count 注数
     * @return List<Map<String, List<Integer>>> 多注号码的列表
     */
    public static List<Map<String, List<Integer>>> generateMultiple(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("注数必须大于0");
        }

        List<Map<String, List<Integer>>> resultList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            resultList.add(generateSingle());
        }
        return resultList;
    }

    /**
     * 判断两个列表是否包含任意一个元素
     * @param list1 列表1
     * @param list2 列表2
     * @return 是否包含任意一个元素
     */
    public static <T> boolean containsAny(List<T> list1, List<T> list2) {
        Set<T> set2 = new HashSet<>(list2);
        return list1.stream().anyMatch(set2::contains);
    }

    /**
     * 判断两个列表是否包含相同的元素
     * @param list1 列表1
     * @param list2 列表2
     * @return 是否包含相同的元素
     */
    public static <T> boolean containsSameElements(List<T> list1, List<T> list2) {
        return new HashSet<>(list1).equals(new HashSet<>(list2));
    }

    /**
     * 格式化输出双色球号码
     * @param ticket 单注号码
     */
    public static void printTicket(Map<String, List<Integer>> ticket) {
        List<Integer> redBalls = ticket.get("red");
        List<Integer> blueBalls = ticket.get("blue");

        if (checkOld(blueBalls, redBalls)) {
            return;
        }

        System.out.print("红球：");
        for (int i = 0; i < redBalls.size(); i++) {
            System.out.printf("%02d ", redBalls.get(i));
        }
        System.out.print(" 蓝球：");
        for (Integer blueBall : blueBalls) {
            System.out.printf("%02d", blueBall);
        }
        System.out.println();
    }

    /**
     * 是否验证历史数据
     * @param blueBalls
     * @param redBalls
     * @return
     */
    private static boolean checkOld(List<Integer> blueBalls, List<Integer> redBalls) {
        return false;
        /*if (!blueBalls.get(0).equals(4)) {
            return true;
        }
        
        List<Integer> oldRedBalls = Arrays.asList(1, 3, 5, 18, 29, 32);
        
        // 判断两个列表是否包含任意一个元素
        *//*boolean result = containsAny(redBalls, oldRedBalls); // true
             if (!result) {
               return;
             }*//*
                   // 判断两个列表是否包含任意一个元素
                   boolean result = containsSameElements(redBalls, oldRedBalls); // true
                   if (!result) {
                    return true;
                   }
                   return false;*/
    }

    /**
     * 生成并打印多注号码（带序号）
     * @param count 注数
     */
    public static void generateAndPrint(int count) {
        System.out.println("========== 双色球号码生成器 ==========");
        System.out.println("注数：" + count + "\n");

        List<Map<String, List<Integer>>> tickets = generateMultiple(count);
        for (int i = 0; i < tickets.size(); i++) {
            //System.out.printf("第%02d注：", i + 1);
            printTicket(tickets.get(i));
        }

        System.out.println("\n温馨提示：彩票为随机游戏，请理性投注！");
    }

    // 高级功能：生成不重复的号码组合（确保多注之间红球不重复）
    public static List<Map<String, List<Integer>>> generateUniqueCombinations(int count) {
        if (count <= 0 || count > 1107568) { // C(33,6)*16的最大组合数
            throw new IllegalArgumentException("注数超出范围");
        }

        Set<String> existingCombinations = new HashSet<>();
        List<Map<String, List<Integer>>> resultList = new ArrayList<>();
        Random random = new Random();

        while (resultList.size() < count) {
            List<Integer> redBalls = new ArrayList<>();

            while (redBalls.size() < 6) {
                int num = random.nextInt(33) + 1;
                if (!redBalls.contains(num)) {
                    redBalls.add(num);
                }
            }
            Collections.sort(redBalls);

            // 使用红球组合作为唯一标识
            String redKey = redBalls.toString();

            if (!existingCombinations.contains(redKey)) {
                existingCombinations.add(redKey);

                int blueBall = random.nextInt(16) + 1;
                List<Integer> blueBalls = new ArrayList<>();
                blueBalls.add(blueBall);

                Map<String, List<Integer>> ticket = new HashMap<>();
                ticket.put("red", new ArrayList<>(redBalls));
                ticket.put("blue", blueBalls);
                resultList.add(ticket);
            }
        }

        return resultList;
    }

    // 测试主方法
    public static void main(String[] args) {

        // 1 3 5 18 29 32 4
        // 示例1：生成并打印5注
        //System.out.println("【示例1：生成5注随机号码】");
        //generateAndPrint(1000000);

        // 示例2：生成单注并获取详细数据
        /*System.out.println("【示例2：生成单注号码并获取数据】");
        Map<String, List<Integer>> singleTicket = generateSingle();
        System.out.println("红球列表：" + singleTicket.get("red"));
        System.out.println("蓝球列表：" + singleTicket.get("blue"));
        System.out.print("格式化输出：");
        printTicket(singleTicket);
        
        System.out.println("\n====================================\n");*/

        // 示例3：生成3注不重复的红球组合
        int count = 10;
        System.out.println("========== 双色球号码生成器 ==========");
        System.out.println("注数：" + count + "\n");
        List<Map<String, List<Integer>>> uniqueTickets = generateUniqueCombinations(count);
        for (int i = 0; i < uniqueTickets.size(); i++) {
            //System.out.printf("第%02d注：", i + 1);
            printTicket(uniqueTickets.get(i));
        }

        System.out.println("\n====================================\n");
    }
}