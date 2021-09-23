package com.example.demo.webserver;

import cn.cjk.timerM.domain.MachineRelate;
import cn.cjk.timerM.domain.TaskEntity;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.controller.HostController;
import com.example.demo.model.MachineRelateResponseVO;
import com.example.demo.model.ThreadConstant;
import com.example.demo.utils.SpringBeanHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Timer Tsui
 * @version 1.0
 * @description
 * @date 2021/09/23 17:12
 **/

@ServerEndpoint(value = "/websocketTest/{userId}")
@Component
public class DemoWebsocket {

    @Resource
    private RedisTemplate<String, TaskEntity> redisTemplate = SpringBeanHolder.getBean("redisTemplate", RedisTemplate.class);

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
    public void onOpen(@PathParam("userId") String userId, Session session) throws IOException, InterruptedException {
        logger.debug("新连接：{}", userId);
        //首先要判断该ID是否已经加入，如果已经加入不重复加入
        if (!isEmpty(userId)) {
            this.userId = userId;
            this.session = session;
            clients.put(userId, this);
            logger.info("现在连接的客户编码为：" + userId);
        }
        while (session.isOpen()) {
            final Map<String, Object> config = getConfig();
            session.getBasicRemote().sendText(config.toString());
            TimeUnit.SECONDS.sleep(1);
        }

    }


    public JSONObject getConfig() {
        Map<String, Object> maps = new HashMap<>();
        List<MachineRelateResponseVO> machineRelateList = new ArrayList<>();

        HashOperations<String, String, MachineRelate> hashOperations1 = redisTemplate.opsForHash();
        HashOperations<String, String, TaskEntity> hashOperations2 = redisTemplate.opsForHash();
        Map<String, MachineRelate> configMap = hashOperations1.entries(ThreadConstant.MACHINE_CONFIG);
        Map<String, TaskEntity> runTaskMap = hashOperations2.entries(ThreadConstant.TASK_RUN_KEY);
        Map<String, Long> runCountMap = runTaskMap.values().stream().collect(Collectors.
                groupingBy(ts -> ts.getHost() + "_" + ts.getPort(), Collectors.counting()));
        Map<String, TaskEntity> queueTaskMap = hashOperations2.entries(ThreadConstant.TASK_QUEUE_KEY);
        Map<String, Long> queueCountMap = queueTaskMap.values().stream().collect(Collectors.
                groupingBy(ts -> ts.getHost() + "_" + ts.getPort(), Collectors.counting()));

        int id = 1;
        for (Map.Entry<String, MachineRelate> entry : configMap.entrySet()) {
            MachineRelate machineRelate = entry.getValue();
            MachineRelateResponseVO machineRelateResponseVO = new MachineRelateResponseVO(machineRelate);
            machineRelateResponseVO.setId(id);
            machineRelateResponseVO.setRun(runCountMap.getOrDefault(entry.getKey(), 0L));
            machineRelateResponseVO.setQueue(queueCountMap.getOrDefault(entry.getKey(), 0L));
            machineRelateList.add(machineRelateResponseVO);
            id++;
        }
        maps.put("recordsTotal", configMap.size());
        maps.put("recordsFiltered", configMap.size());
        maps.put("data", machineRelateList);
        System.err.println(machineRelateList);
        return new JSONObject(maps);
//        return maps;
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
//        error.printStackTrace();
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
