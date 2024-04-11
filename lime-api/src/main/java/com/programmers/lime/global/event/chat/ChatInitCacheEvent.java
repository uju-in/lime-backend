package com.programmers.lime.global.event.chat;

import java.util.List;

import com.programmers.lime.domains.chatroom.model.ChatRoomInfo;

public record ChatInitCacheEvent(
	List<ChatRoomInfo> chatRoomInfos
) {
}
