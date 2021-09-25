package com.example.demo.webserver;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Timer Tsui
 * @version 1.0
 * @description websocket服务端, 多例的，一次websocket连接对应一个实例
 * @date 2021/09/23 16:24
 **/
@Component
@ServerEndpoint("/{name}/{toName}")
public class WebSocketServer {


    /**
     * 用来记录当前在线连接数。设计成线程安全的。
     */
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    /**
     * 用于保存uri对应的连接服务，{uri:WebSocketServer}，设计成线程安全的
     */
    private static ConcurrentHashMap<String, WebSocketServer> webSocketServerMAP = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 客户端消息发送者
     */
    private String name;
    /**
     * 客户端消息接受者
     */
    private String toName;
    /**
     * 连接的uri
     */
    private String uri;


    /**
     * 连接建立成功时触发，绑定参数
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     * @param name
     * @param toName
     * @throws IOException
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("name") String name, @PathParam("toName") String toName) throws IOException {
        this.session = session;
        this.name = name;
        this.toName = toName;
        this.uri = session.getRequestURI().toString();
        WebSocketServer webSocketServer = webSocketServerMAP.get(uri);
        //同样业务的连接已经在线，则把原来的挤下线。
        session.setMaxIdleTimeout(10 * 1000L);
        if (webSocketServer != null) {
            webSocketServer.session.getBasicRemote().sendText(uri + "重复连接被挤下线了");
            //关闭连接，触发关闭连接方法onClose()
            webSocketServer.session.close();
        }
        //保存uri对应的连接服务
        webSocketServerMAP.put(uri, this);
        // 在线数加1
        addOnlineCount();
        sendMessage(webSocketServerMAP + "当前在线连接数:" + getOnlineCount());

        System.out.println(this + "有新连接加入！当前在线连接数：" + getOnlineCount());
    }

    /**
     * 连接关闭时触发，注意不能向客户端发送消息了
     *
     * @throws IOException
     */
    @OnClose
    public void onClose() throws IOException {
        //删除uri对应的连接服务
        webSocketServerMAP.remove(uri);
        // 在线数减1
        reduceOnlineCount();
        System.out.println("有一连接关闭！当前在线连接数" + getOnlineCount());
    }

    /**
     * 收到客户端消息后触发,分别向2个客户端（消息发送者和消息接收者）推送消息
     *
     * @param message 客户端发送过来的消息
     *                //     * @param session 可选的参数
     * @throws IOException
     */
    @OnMessage
    public void onMessage(String message) throws IOException {
        //服务器向消息发送者客户端发送消息
        this.session.getBasicRemote().sendText("发送给" + toName + "的消息:" + message);
        //获取消息接收者的客户端连接
        StringBuilder receiverUri = new StringBuilder("/");
        receiverUri.append(toName)
                .append("/")
                .append(name);
        WebSocketServer webSocketServer = webSocketServerMAP.get(receiverUri.toString());
        if (webSocketServer != null) {
            webSocketServer.session.getBasicRemote().sendText("来自" + name + "的消息:" + message);
        }
        /*// 群发消息
        Collection<WebSocketServer> collection = webSocketServerMAP.values();
        for (WebSocketServer item : collection) {
            try {
                item.sendMessage("收到群发消息:" + message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }*/
    }

    /**
     * 通信发生错误时触发
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 向客户端发送消息
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 获取在线连接数
     *
     * @return
     */
    public static int getOnlineCount() {
        return onlineCount.get();
    }

    /**
     * 原子性操作，在线连接数加一
     */
    public static void addOnlineCount() {
        onlineCount.getAndIncrement();
    }

    /**
     * 原子性操作，在线连接数减一
     */
    public static void reduceOnlineCount() {
        onlineCount.getAndDecrement();
    }

}
