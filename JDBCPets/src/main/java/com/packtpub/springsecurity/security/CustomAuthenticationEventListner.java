package com.packtpub.springsecurity.security;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.GenericApplicationListenerAdapter;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEventListner implements ApplicationListener<AbstractAuthenticationEvent> {

	@Override
	public void onApplicationEvent(AbstractAuthenticationEvent event) {
		// TODO Auto-generated method stub
		//GenericApplicationListenerAdapter
		
		System.out.println("Received event of type : " + event.getClass().getName() + " : " + event.toString());
	}

}
