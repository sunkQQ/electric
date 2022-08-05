package com.electric.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * WebSocket
 *
 * @Author sunk
 * @Date 2021/12/10
 */
@ServerEndpoint(value = "/websocket")
public class WebSocketTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketTest.class);

    /**
     * 线程安全的静态变量，表示在线连接数
     */
    private static volatile int onclineCount = 0;

    // 用来存放每个客户端对应的WebSocketTest对象，适用于同时志多个客户端通信
    public static CopyOnWriteArraySet<WebSocketTest> webSocketSet = new CopyOnWriteArraySet<>();
    // 若要实现服务端与指定客户端通住的话，可以使用Map来存放，其中Key可以为用户标识
    public static ConcurrentHashMap<Session, Object> webSocketMap = new ConcurrentHashMap<>();

    // 与某个客户端的连接会话， 通过它实现定向推送（只推送给某个用户）
    private Session session;

    /**
     * 建立连接成功调用的方法
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        // 添加到set中
        webSocketSet.add(this);
        // 添加到map中
        webSocketMap.put(session, this);
        // 添加在线人数
        addOnlineCount();
        System.out.println("新人加人， 当前在线人数为：" + getOnclineCount());
    }

    /**
     * 关闭连招调用的方法
     *
     * @param closeSession
     */
    @OnClose
    public void onClose(Session closeSession) {
        webSocketMap.remove(session);
        webSocketSet.remove(this);
        subOnlineCount();
        System.out.println("有人离开，当前在线人数为：" + getOnclineCount());
    }

    /**
     * 收到客户端消息调用的方法
     * @param message
     * @param mysession
     * @throws Exception
     */
    @OnMessage
    public void onMessage(String message, Session mysession) throws Exception {
        for (WebSocketTest item : webSocketSet) {
            item.sendAllMessage(message);
        }
    }

    public void sendAllMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 获取在线人数
     *
     * @return
     */
    public static synchronized int getOnclineCount() {
        return onclineCount;
    }

    /**
     * 添加在线人+1
     */
    public static synchronized void addOnlineCount() {
        onclineCount++;
    }

    /**
     * 减少在线人-1
     */
    public static synchronized void subOnlineCount() {
        onclineCount--;
    }
}
