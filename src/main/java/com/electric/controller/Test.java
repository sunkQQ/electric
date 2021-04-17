package com.electric.controller;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @Author Administrator
 * @Date 2020-9-17
 *
 */

public class Test {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        Integer i = 0;
        while (i <= 10) {
            list.add(i.toString());
            i++;
        }
        //        list.forEach(str -> str.toString());

        //        System.out.println();
        //        System.out.println();
        //        System.out.println();
        list.stream().map(str -> (Integer.parseInt(str) % 2 == 0) ? str : "").forEach(System.out::println);
    }
}
