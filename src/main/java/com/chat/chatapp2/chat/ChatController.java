package com.chat.chatapp2.chat;// ChatController.java
import com.chat.chatapp2.chat.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private ChatHandler chatHandler;

    @GetMapping("/")
    public String nicknamePage() {
        return "nickname";
    }

    @GetMapping("/rooms")
    public String roomsPage(Model model) {
        List<ChatRoom> rooms = chatRoomService.getAllRooms();
        model.addAttribute("rooms", rooms);
        return "rooms";
    }

    @PostMapping("/createRoom")
    public String createRoom(@RequestParam String roomName) {
        chatRoomService.createRoom(roomName);
        return "redirect:/rooms";
    }

    @GetMapping("/chat")
    public String chatPage(@RequestParam String roomName, Model model) {
        ChatRoom room = chatRoomService.getRoom(roomName);
        if (room == null) {
            return "redirect:/rooms";
        }
        model.addAttribute("roomName", roomName);
        return "chat";
    }

    @GetMapping("/chat/history")
    @ResponseBody
    public List<String> getChatHistory(@RequestParam String roomId) {
        return chatHandler.getMessagesByRoomId(roomId);
    }
}