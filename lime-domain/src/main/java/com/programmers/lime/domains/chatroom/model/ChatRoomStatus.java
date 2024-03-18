package com.programmers.lime.domains.chatroom.model;

public enum ChatRoomStatus {

	OPEN("열림"),
	CLOSE("닫힘");
	private final String description;

	ChatRoomStatus(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
