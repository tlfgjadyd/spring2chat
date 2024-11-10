package com.chat.chatapp2.chat.service;

import com.chat.chatapp2.chat.ChatRoom;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class ChatRoomService {
    private final ConcurrentMap<String, ChatRoom> rooms = new ConcurrentHashMap<>();

    public void createRoom(String name) {
        ChatRoom room = new ChatRoom(name);
        rooms.put(name, room);
    }

    public ChatRoom getRoom(String name) {
        return rooms.get(name);
    }

    public List<ChatRoom> getAllRooms() {
        return new ArrayList<>(rooms.values());
    }
}