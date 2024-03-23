package com.programmers.lime.domains.chatroom.model;

public record ChatRoomInfo(
	Long chatRoomId,
	String chatRoomName,
	ChatRoomType chatRoomType,
	ChatRoomStatus chatRoomStatus,
	int roomMaxMemberCount,
	boolean isJoined,
	Long currentMemberCount
) {
}
