package com.chat.chatapp2.controller;


import com.chat.chatapp2.dao.ChatRepository;
import com.chat.chatapp2.dto.ChatRoom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequestMapping("/chat")
public class ChatRoomController {

    @Autowired
    private ChatRepository chatRepository;

    // 채팅 리스트 화면
    @GetMapping("/chatlist")
    public String goChatRoom(Model model){

        model.addAttribute("list", chatRepository.findAllRoom());
//        model.addAttribute("user", "hey");
        log.info("SHOW ALL ChatList {}", chatRepository.findAllRoom());
        return "roomlist";
    }

    // 채팅방 생성
    @PostMapping("/createroom")
    public String createRoom(@RequestParam String name, RedirectAttributes rttr) {
        ChatRoom room = chatRepository.createChatRoom(name);
        log.info("CREATE Chat Room {}", room);
        rttr.addFlashAttribute("roomName", room);
        return "redirect:/chat/chatlist";
    }

    // 채팅방 입장 화면
    // @PathVariable : url 에 넘어오는 변수를 매개변수로 받을 수 있게 하는 어노테이션
    // {roomId} 가 url 변수 -> String 타입 roomId 로 받게됨
    @GetMapping("/room")
    public String roomDetail(Model model, String roomId){

        log.info("roomId {}", roomId);
        model.addAttribute("room", chatRepository.findRoomById(roomId));
        return "/chatroom";
    }

}