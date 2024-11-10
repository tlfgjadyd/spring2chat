package com.chat.chatapp2.chat;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom {
    @Getter
    private String name;
    private List<String> messages;

    public ChatRoom(String name) {
        this.name = name;
        this.messages = new ArrayList<>();
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    public void addMessage(String message) {
        messages.add(message);
    }
}