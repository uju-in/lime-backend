package com.programmers.lime.global.config.chat.type;

public enum MessageDomainType {

	CHAT("채팅");

	private final String description;

	MessageDomainType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
