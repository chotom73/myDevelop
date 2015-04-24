package com.chat.config.spring;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.chat.domain.SessionProfanity;
import com.chat.event.ParticipantRepository;
import com.chat.event.PresenceEventListener;
import com.chat.util.ProfanityChecker;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.chat" })
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter { 
	
	public static class Destinations {
		private Destinations() {}
		
		private static final String LOGIN  = "/topic/chat.login";
		private static final String LOGOUT = "/topic/chat.logout";
	}
	
	private static final int MAX_PROFANITY_LEVEL = 5;
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**/*").addResourceLocations("classpath:/static/");
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/index.htm").setViewName("index");
	}
	
	@Override
    public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
	
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");		
		return viewResolver;
	}
	
	
	@Bean
	@Description("Tracks user presence (join / leave) and broacasts it to all connected users")
	public PresenceEventListener presenceEventListener(SimpMessagingTemplate messagingTemplate) {
		PresenceEventListener presence = new PresenceEventListener(messagingTemplate, participantRepository());
		presence.setLoginDestination(Destinations.LOGIN);
		presence.setLogoutDestination(Destinations.LOGOUT);
		return presence;
	}
	
	@Bean 
	@Description("Keeps connected users")
	public ParticipantRepository participantRepository() {
		return new ParticipantRepository();
	}
	
	@Bean
	@Scope(value = "websocket", proxyMode =ScopedProxyMode.TARGET_CLASS)
	@Description("Keeps track of the level of profanity of a websocket session")
	public SessionProfanity sessionProfanity() {
		return new SessionProfanity(MAX_PROFANITY_LEVEL);
	}
	
	@Bean
	@Description("Utility class to check the number of profanities and filter them")
	public ProfanityChecker profanityFilter() {
		Set<String> profanities = new HashSet<>(Arrays.asList("damn", "crap", "ass"));
		ProfanityChecker checker = new ProfanityChecker();
		checker.setProfanities(profanities);
		return checker;
	}
}
