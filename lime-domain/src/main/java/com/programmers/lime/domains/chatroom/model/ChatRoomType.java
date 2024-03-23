package com.programmers.lime.domains.chatroom.model;

public enum ChatRoomType {

	PRIVATE("비공개"),
	PUBLIC("공개");
	private final String description;

	ChatRoomType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
