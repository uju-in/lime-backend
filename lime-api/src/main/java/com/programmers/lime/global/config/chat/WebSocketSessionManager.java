package com.programmers.lime.global.config.chat;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class WebSocketSessionManager {

	private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

	public void registerSession(final WebSocketSession session) {
		sessions.put(session.getId(), session);
	}

	public void removeSession(final String sessionId) {
		sessions.remove(sessionId);
	}

	public WebSocketSession getSession(final String sessionId) {
		return sessions.get(sessionId);
	}

	public void closeSession(final String sessionId) throws Exception {
		WebSocketSession session = sessions.get(sessionId);
		if (session != null && session.isOpen()) {
			session.close();
			removeSession(sessionId);
		}
	}
}