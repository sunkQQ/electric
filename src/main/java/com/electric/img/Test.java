package com.electric.img;

import com.electric.util.SM4ToolUtil;

/**
 * @author sunkang
 * 2025/10/30
 */
public class Test {

    public static void main(String[] args) throws Exception {
        decrypt();
    }

    private static void decrypt() throws Exception {
        String str = "e68e4e54fbf3c4bb6621cc381550b43f465ee14d043870c72d4a9af933ed2eaccb577690fc44500921b8946124b826805903f8109ea78ebfc70d5ea9d452b63b5b453790b95d225ec32a0448a6624977aad50c240a148364e9b77b7910622da7356f7c36c297c458e8a07e5a8542d3b92ffef2eae4199125ac2fd8dfceaca21bf4ad407a3739f5d6bb34fefc760c0af8af1aca81703603bfb6accd65cc1881a1cc24d639cea5bbfd789b5c2117b4c89449169233e27a51ee5c0ea56b6abab659";

        String key = "A7CB002E62B25E468F331470BFB5A2AA";
        /*String st = "{\"loginInfo\":\"Cy3yPUYjkX: 福州大学-旗山校区|Cy3yVUlkzy: 12号楼|Cy3yZrTLiz: 2层|Cy3ycuxrdK: 214|Cy3ykfSjyN:\",\"project_id\":\"1810191652457222\",\"transID\":\"20250918100000000001\",\"txCode\":\"IPSPT10007\"}";
        String signStr = SM4ToolUtil.encryptEcb(key, st);
        System.out.println(signStr);*/
        String data = SM4ToolUtil.decryptGcm(key, str);
        System.out.println(data);
    }
}
