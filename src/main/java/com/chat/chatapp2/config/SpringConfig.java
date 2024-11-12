package com.chat.chatapp2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class SpringConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // stomp 접속 주소 url => /ws-stomp
        registry.addEndpoint("/ws-stomp") // 연결될 엔드포인트
                .setAllowedOriginPatterns("*") //allowedOrigins는 쓰면 안되나봐? 왜지??뭔차이지
                /*
                allowCredentials가 참인 경우
                , 허용된 오리진은 "접근-제어-허용-오리진" 응답 헤더에 특수 값 "*"을 포함할 수 없으므로
                이 값을 포함할 수 없습니다.
                오리진 세트에 자격 증명을 허용하려면 해당 자격 증명을 명시적(?)으로 나열하거나
                대신 "allowedOriginPatterns"를 사용하는 것을 고려하세요
                 */
                .withSockJS(); // SocketJS 를 연결한다는 설정
/*
        registry.addEndpoint("/ws-stomp")
                .setAllowedOriginPatterns("*");
*/
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메시지를 구독하는 요청 url => 즉 메시지 받을 때
        registry.enableSimpleBroker("/sub");

        // 메시지를 발행하는 요청 url => 즉 메시지 보낼 때
        registry.setApplicationDestinationPrefixes("/pub");
    }
}