package com.chotom.test;

import java.util.ArrayList;

import org.springframework.web.socket.WebSocketSession;

public class SessionManager {

	private static SessionManager sessionManager;
	ArrayList<WebSocketSession> webSocketSession;
	
	private SessionManager() {
		webSocketSession = new ArrayList<WebSocketSession>();
	}
	
	public static SessionManager getInstance() {
		if (sessionManager == null) {
			sessionManager = new SessionManager(); 
		}
		return sessionManager;
	}

	public void add(WebSocketSession session) {
		webSocketSession.add(session);
	}

	public ArrayList<WebSocketSession> getList() {
		return webSocketSession;
	}

	public void remove(WebSocketSession session) {
		webSocketSession.remove(session);
		
	}

}
