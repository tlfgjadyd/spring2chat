package com.chat.chatapp2.chat;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.util.UriTemplate;

public class RoomUtil {

    public static String extractRoomId(WebSocketSession session) {
        String path = session.getUri().getPath();
        UriTemplate template = new UriTemplate("/chat/{roomId}");
        return template.match(path).get("roomId");
    }
}