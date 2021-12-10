package com.electric.socket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 前后端交互的类，实现消息的接收的接受推送（自己发送给所有人（不包括自己）}
 *
 * @Author sunk
 * @Date 2021/12/10
 */
@Slf4j
@ServerEndpoint(value = "/test/oneToMany")
@Component
public class OneToManyWebSocket {

    /** 记录当前在线连接数 */
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    /** 存放所有在线的客户端 */
    private static Map<String, Session> clients = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        // 在线人数加1
        onlineCount.incrementAndGet();
        clients.put(session.getId(), session);
        log.info("有新连接加入：{}， 当前在线人数为：{}", session.getId(), onlineCount.get());
    }

    /**
     * 连接关闭调用的方法
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        // 在线数减1
        onlineCount.decrementAndGet();
        clients.remove(session.getId());
        log.info("有一连接关闭：{}，当前在线人数为：{}", session.getId(), onlineCount.get());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发过来的消息
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("服务端收到客户端[{}]的消息：{}", session.getId(), message);
        this.sendMessage(message, session);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    private void sendMessage(String message, Session fromSession) {
        for (Map.Entry<String, Session> sessionEntry : clients.entrySet()) {
            Session toSession = sessionEntry.getValue();
            // 排除掉自己
            if (!fromSession.getId().equals(toSession.getId())) {
                log.info("服务端客户端[{}]发送消息{}", toSession.getId(), message);
                toSession.getAsyncRemote().sendText(message);
            }
        }
    }
}
