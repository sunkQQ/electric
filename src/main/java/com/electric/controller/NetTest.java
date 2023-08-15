package com.electric.controller;

import org.apache.commons.net.util.SubnetUtils;

/**
 * @author sunk
 * @date 2023/08/08
 */
public class NetTest {

    public static void main(String[] args) {

        /*String ss = "183.131.4.48/28,101.71.139.208/28,117.147.208.128/28,115.231.235.168/29,45.250.38.40/29,112.13" +
                ".169.96/29,119.37.193.224/28,202.91.230.224/28,39.170.32.160/28,115.236.174.32/29";
        String[] sts = ss.split(",");
        for (String st : sts) {
            SubnetUtils utils = new SubnetUtils(st);
            String[] adress = utils.getInfo().getAllAddresses();
            for(String oneAdd : adress){
                System.out.println(oneAdd);
            }
        }*/

        String subnet = "202.91.230.224/28";
        SubnetUtils utils = new SubnetUtils(subnet);
        String[] adress = utils.getInfo().getAllAddresses();
        for(String oneAdd : adress){
            System.out.println(oneAdd);
        }
    }
}
