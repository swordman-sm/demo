package com.example.demo.controller;

import com.example.demo.webserver.DemoWebsocket;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Timer Tsui
 * @version 1.0
 * @description
 * @date 2021/09/23 17:15
 **/
@Controller
@RequestMapping(value = "/queue")
public class WebSocketController {

    @RequestMapping(value = "/websocket")
    @ResponseBody
    public void websocket(String message) {
        DemoWebsocket.sendMessageToUser("user000", message);
    }


}
