package com.electric.controller;

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

        /*String subnet = "202.91.230.224/28";
        SubnetUtils utils = new SubnetUtils(subnet);
        String[] adress = utils.getInfo().getAllAddresses();
        for(String oneAdd : adress){
            System.out.println(oneAdd);
        }*/

        /*String str = "四川轻化工大学宜宾校区一期3号楼3层A303,四川轻化工大学李白河校区B2楼4层B431,四川轻化工大学李白河校区A3楼3层B309,四川轻化工大学李白河校区A1楼7层B715,四川轻化工大学宜宾校区一期1号楼4层B403,四川轻化工大学李白河校区A1楼2层B219,四川轻化工大学李白河校区B2楼3层B316,四川轻化工大学宜宾校区一期3号楼5层E522,四川轻化工大学李白河校区A2楼3层A313,四川轻化工大学李白河校区A1楼2层B219,四川轻化工大学李白河校区A1楼2层B208,四川轻化工大学宜宾校区一期4号楼5层C508,四川轻化工大学李白河校区B2楼4层B431,四川轻化工大学李白河校区B2楼4层B427,四川轻化工大学李白河校区A2楼3层A328,四川轻化工大学宜宾校区一期3号楼4层B409,四川轻化工大学李白河校区A1楼4层B402,四川轻化工大学宜宾校区一期3号楼4层A423,四川轻化工大学宜宾校区一期4号楼2层D219,四川轻化工大学李白河校区B2楼1层B132,四川轻化工大学李白河校区B2楼4层B427,四川轻化工大学李白河校区B2楼6层A626,四川轻化工大学宜宾校区一期3号楼6层E611,四川轻化工大学李白河校区B2楼6层B608,四川轻化工大学宜宾校区一期3号楼6层E603,四川轻化工大学宜宾校区一期3号楼1层E124,四川轻化工大学李白河校区A1楼6层A626,四川轻化工大学宜宾校区一期3号楼4层A423,四川轻化工大学李白河校区B2楼4层B427,四川轻化工大学李白河校区B1楼6层A630,四川轻化工大学宜宾校区一期3号楼2层C211,四川轻化工大学宜宾校区一期4号楼5层B508,四川轻化工大学李白河校区B2楼4层B427,四川轻化工大学李白河校区A1楼6层A626,四川轻化工大学宜宾校区一期3号楼2层C211,四川轻化工大学宜宾校区一期3号楼4层A422,四川轻化工大学李白河校区B2楼6层A626,四川轻化工大学李白河校区B2楼3层B334,四川轻化工大学李白河校区B2楼3层B323,四川轻化工大学李白河校区B2楼3层B318,四川轻化工大学李白河校区B2楼3层B315,四川轻化工大学宜宾校区一期3号楼3层A318,四川轻化工大学李白河校区A1楼2层A225,四川轻化工大学宜宾校区二期11号楼2层C221,四川轻化工大学宜宾校区二期11号楼2层C221,四川轻化工大学宜宾校区一期3号楼2层C217,四川轻化工大学李白河校区A2楼3层A313,四川轻化工大学李白河校区A1楼2层B219,四川轻化工大学宜宾校区二期11号楼2层B203,四川轻化工大学宜宾校区一期3号楼1层D103,四川轻化工大学李白河校区A1楼2层A225,四川轻化工大学李白河校区A1楼2层A225,四川轻化工大学李白河校区B2楼1层B132,四川轻化工大学李白河校区A2楼3层A313,四川轻化工大学李白河校区B2楼3层B315,四川轻化工大学李白河校区B2楼3层B315,四川轻化工大学宜宾校区一期1号楼4层B403,四川轻化工大学宜宾校区一期1号楼4层B403,四川轻化工大学李白河校区B2楼5层B510,四川轻化工大学李白河校区B2楼4层B427,四川轻化工大学李白河校区B2楼1层B132,四川轻化工大学李白河校区B2楼3层B316,四川轻化工大学李白河校区B1楼12层B1215,四川轻化工大学李白河校区A4楼6层B615,四川轻化工大学宜宾校区二期11号楼3层C319,四川轻化工大学李白河校区B2楼4层B427,四川轻化工大学宜宾校区一期3号楼6层E611,四川轻化工大学李白河校区B2楼4层B427,四川轻化工大学李白河校区B2楼2层C229,四川轻化工大学李白河校区A3楼2层B213,四川轻化工大学宜宾校区一期3号楼6层E611,四川轻化工大学李白河校区B2楼2层C229,四川轻化工大学李白河校区B2楼6层A611,四川轻化工大学宜宾校区一期3号楼2层C221,四川轻化工大学李白河校区B1楼11层B1111,四川轻化工大学宜宾校区一期3号楼3层E307,四川轻化工大学李白河校区B1楼9层A905,四川轻化工大学宜宾校区一期4号楼3层D316,四川轻化工大学宜宾校区一期3号楼6层C606,四川轻化工大学李白河校区B2楼1层B132,四川轻化工大学李白河校区A2楼10层A1007,四川轻化工大学宜宾校区一期3号楼3层A320,四川轻化工大学李白河校区B2楼3层C303,四川轻化工大学宜宾校区一期3号楼2层D208,四川轻化工大学李白河校区A1楼9层B913,四川轻化工大学李白河校区A1楼9层B907,四川轻化工大学李白河校区B2楼3层B316,四川轻化工大学宜宾校区一期2号楼3层E315,四川轻化工大学李白河校区A2楼10层A1002,四川轻化工大学李白河校区B2楼3层C303,四川轻化工大学李白河校区B2楼3层C303,四川轻化工大学李白河校区B2楼3层B316,四川轻化工大学李白河校区B2楼3层B316,四川轻化工大学宜宾校区一期3号楼3层A318,四川轻化工大学李白河校区B1楼1层C127,四川轻化工大学李白河校区A4楼2层B206,四川轻化工大学李白河校区B2楼2层C218,四川轻化工大学李白河校区B1楼5层C530,四川轻化工大学李白河校区B1楼12层C1225";
        List<String> list = Arrays.stream(str.split(",")).distinct().collect(Collectors.toList());
        for (String s : list) {
            System.out.println(s);
        }*/
        /*String ADDRESS = "http://ddfkdev.jiaxingren.com/open/user/getAccByPhone";
        Map<String, String> map = new HashMap<>();
        map.put("phones", "18710369301");
        String result = HttpClientUtils.post(ADDRESS, map);
        System.out.println(result);*/

    }
}
