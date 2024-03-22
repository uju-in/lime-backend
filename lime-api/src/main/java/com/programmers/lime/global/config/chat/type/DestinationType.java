package com.programmers.lime.global.config.chat.type;

public enum DestinationType {

	PRIVATE_CHAT_ROOM("PRIVATE_CHAT_ROOM"),
	PUBLIC_CHAT_ROOM("PUBLIC_CHAT_ROOM");

	private final String description;

	DestinationType(final String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
