package org.group4.travelexpertsapi.config;

import org.group4.travelexpertsapi.utils.WebSocketHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat") // WebSocket endpoint
                .addInterceptors(new WebSocketHandshakeInterceptor())
                .setAllowedOriginPatterns("*")
                .withSockJS(); // Optional fallback for browsers
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue"); // broadcast + private
        registry.setApplicationDestinationPrefixes("/app"); // for client send
    }
}

