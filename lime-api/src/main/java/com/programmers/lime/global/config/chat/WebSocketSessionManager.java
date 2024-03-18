package com.programmers.lime.global.config.chat;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionManager {

	private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

	public void registerSession(WebSocketSession session) {
		sessions.put(session.getId(), session);
	}

	public void removeSession(String sessionId) {
		sessions.remove(sessionId);
	}

	public WebSocketSession getSession(String sessionId) {
		return sessions.get(sessionId);
	}

	public void closeSession(String sessionId) throws Exception {
		WebSocketSession session = sessions.get(sessionId);
		if (session != null && session.isOpen()) {
			session.close();
			removeSession(sessionId);
		}
	}
}