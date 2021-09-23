package com.example.demo.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Timer Tsui
 * @version 1.0
 * @description
 * @date 2021/09/23 17:12
 **/

@ServerEndpoint(value = "/websocketTest/{userId}")
@Component
public class DemoWebsocket {
    private static Logger logger = LoggerFactory.getLogger(DemoWebsocket.class);

    private String userId;
    /**
     * 会话
     */
    private Session session;

    /**
     * 以用户的姓名为key,websocket为对象保存起来
     */
    private static Map<String, DemoWebsocket> clients = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) {
        logger.debug("新连接：{}", userId);
        //首先要判断该ID是否已经加入，如果已经加入不重复加入
        if (!isEmpty(userId)) {
            this.userId = userId;
            this.session = session;
            clients.put(userId, this);
            logger.info("现在连接的客户编码为：" + userId);
        }
    }

    /**
     * 关闭时执行
     */
    @OnClose
    public void onClose() {
        logger.debug("连接：{} 关闭", this.userId);
    }

    /**
     * 收到消息时执行
     *
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.debug("收到用户{}的消息{}", this.userId, message);
        //回复用户
        session.getAsyncRemote().sendText("收到 " + this.userId + " 的消息:" + message);
    }

    /**
     * 连接错误时执行
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.debug("用户id为：{}的连接发送错误", this.userId);
        error.printStackTrace();
    }

    /**
     * 消息点对点推送
     *
     * @param clientId
     * @param message
     */
    public static void sendMessageToUser(String clientId, String message) {

        DemoWebsocket session = clients.get(clientId);
        if (session != null) {
            if (session.session.isOpen()) {
                session.session.getAsyncRemote().sendText("收到 " + clientId + " 的消息:" + message);
            } else {
                logger.info("用户session关闭");
            }
        } else {
            logger.info("用户session不存在");
        }
    }

    private static boolean isEmpty(String s) {
        return s == null || s.length() == 0 || "".equals(s)
                || "null".equalsIgnoreCase(s)
                || "undefined".equalsIgnoreCase(s);
    }

}
