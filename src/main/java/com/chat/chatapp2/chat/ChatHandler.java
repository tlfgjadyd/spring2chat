package com.chat.chatapp2.chat;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriTemplate;

import java.util.*;

@Component
public class ChatHandler extends TextWebSocketHandler {

    private final Map<String, Set<WebSocketSession>> roomSessions = Collections.synchronizedMap(new HashMap<>());
    private final Map<String, List<String>> chatMessages = Collections.synchronizedMap(new HashMap<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String roomId = extractRoomId(session);
        roomSessions.computeIfAbsent(roomId, k -> new HashSet<>()).add(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String roomId = extractRoomId(session);

        // Add message to chat history
        chatMessages.computeIfAbsent(roomId, k -> new ArrayList<>()).add(message.getPayload());

        // Broadcast message to all sessions in the room
        Set<WebSocketSession> sessions = roomSessions.getOrDefault(roomId, Collections.emptySet());
        for (WebSocketSession sess : sessions) {
            if (sess.isOpen()) {
                sess.sendMessage(message);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String roomId = extractRoomId(session);
        Set<WebSocketSession> sessions = roomSessions.get(roomId);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                roomSessions.remove(roomId);
            }
        }
    }

    private String extractRoomId(WebSocketSession session) {
        String path = session.getUri().getPath();
        UriTemplate template = new UriTemplate("/chat/{roomId}");
        return template.match(path).get("roomId");
    }

    public List<String> getMessagesByRoomId(String roomId) {
        return chatMessages.getOrDefault(roomId, Collections.emptyList());
    }
}