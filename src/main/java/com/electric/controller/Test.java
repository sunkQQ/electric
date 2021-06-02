package com.electric.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Author Administrator
 * @Date 2020-9-17
 *
 */

public class Test {
    public static void main(String[] args) throws IOException {
        /*List<String> list = new ArrayList<>();
        Integer i = 0;
        while (i <= 10) {
        	list.add(i.toString());
        	i++;
        }
        // list.forEach(str -> str.toString());
        
        // System.out.println();
        // System.out.println();
        // System.out.println();
        list.stream().map(str -> (Integer.parseInt(str) % 2 == 0) ? str : "").forEach(System.out::println);*/
        //        String ss = "[\"0.0\"|\"1.0\"|\"2.0\"]";
        //        List<String> list2 = JSONArray.parseArray(ss.replaceAll("\\|", ","), String.class);
        //
        //        System.out.println(StringUtils.join(list2, ","));
        //        System.out.println(list2);.

        //        ：:

        FileReader reader = new FileReader("D://new 3.txt");
        BufferedReader br = new BufferedReader(reader);
        String str = null;
        List<String> list = new ArrayList<>();
        while ((str = br.readLine()) != null) {
            //            System.out.println(str);
            list.add(str);
        }
        //        System.out.println(list);
        Map<String, String> map = new HashMap<>();
        int i = 0;
        for (String string : list) {
            //            System.out.println(string);
            if (map.containsKey(string)) {
                System.out.println(string);
                i++;
            }
            map.put(string, string);
        }
        System.out.println(i);

        //        System.out.println(list.stream().distinct().collect(Collectors.toList()).size());
    }

}
