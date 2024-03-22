package com.programmers.lime.global.event.chat;

public class ChatSessionDeleteEvent {

	private final String sessionId;

	public ChatSessionDeleteEvent(final String sessionId) {
		this.sessionId = sessionId;
	}

	public String sessionId() {
		return sessionId;
	}
}
