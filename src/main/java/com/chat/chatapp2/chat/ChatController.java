package com.chat.chatapp2.chat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChatController {

    @RequestMapping("/")
    public String chat() {
        return "chat";
    }
}