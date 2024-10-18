package com.electric.socket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;
import com.electric.socket.domain.MyMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * 前后端交互的类实现消息的接收推送（自己发送给另一个人）
 *
 * @Author sunk
 * @Date 2021/12/10
 */
@Slf4j
@ServerEndpoint(value = "/test/oneToOne")
@Component
public class OneToOneWebSocket {

    /**
     * 记录当前在线的连接数
     */
    private static AtomicInteger        onlineCount = new AtomicInteger();

    /**
     * 在放所有的在线的客户端
     */
    private static Map<String, Session> clients     = new ConcurrentHashMap<>();

    /**
     * 连接建立成功的调用的方法
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        // 在线数加1
        onlineCount.incrementAndGet();
        clients.put(session.getId(), session);
        log.info("有新连接加入：{}，当前在线人数为：", session.getId(), onlineCount.get());
    }

    /**
     * 连接关闭调用的方法
     *
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        // 在线人数减1
        onlineCount.decrementAndGet();
        clients.remove(session.getId());
        log.info("有一连接关闭：{}， 当前在线人数为：{}", session.getId(), onlineCount.get());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("服务端收到客户端[{}]的消息[{}]", session.getId(), message);
        try {
            MyMessage myMessage = JSON.parseObject(message, MyMessage.class);
            if (myMessage != null) {
                Session toSession = clients.get(myMessage.getUserId());
                if (toSession != null) {
                    this.sendMessage(myMessage.getMessage(), toSession);
                }
            }
        } catch (Exception e) {
            log.error("解析失败：{}", e);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 服务端发送消息给客户端
     *
     * @param message
     * @param toSession
     */
    private void sendMessage(String message, Session toSession) {
        try {
            log.info("服务端给客户端[{}]发送消息[{}]", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("服务端发送消息给客户段失败：{}", e);
        }

    }
}
