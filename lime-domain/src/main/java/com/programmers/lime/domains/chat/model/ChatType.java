package com.programmers.lime.domains.chat.model;

public enum ChatType {

	CHAT("채팅"),
	EXIT("퇴장"),
	JOIN("입장");
	private final String description;

	ChatType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}