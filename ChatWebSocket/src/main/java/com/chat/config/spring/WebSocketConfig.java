package com.chat.config.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.session.ExpiringSession;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractSessionWebSocketMessageBrokerConfigurer<ExpiringSession> {

	@Override
	protected void configureStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws").withSockJS();		
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/queue/", "/topic/");
		registry.setApplicationDestinationPrefixes("/app");
	}

}
