package com.electric.socket.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author sunk
 * @Date 2021/12/10
 */
@Getter
@Setter
@ToString
public class MyMessage {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 消息
     */
    private String message;
}
