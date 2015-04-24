package com.chat.config.spring;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
	
	@Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
		messages.simpDestMatchers("/topic/chat.login", "/topic/chat.logout","/topic/chat.message").denyAll();
		
        messages.anyMessage().authenticated();
    }
}