package com.electric.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nuonuo.open.sdk.NNOpenSDK;

/**
 * 
 * 
 * @Author Administrator
 * @Date 2020-9-17
 *
 */
@Getter
@Setter
@ToString
public class TestDO {
    public static void main(String[] args) {
        NNOpenSDK sdk = NNOpenSDK.getIntance();
    }
}
