package com.programmers.lime.domains.chatroom.application.dto.response;

import java.util.List;

import com.programmers.lime.domains.chatroom.model.ChatRoomInfo;

public record ChatRoomGetServiceListResponse(
	List<ChatRoomInfo> chatRoomInfos
) {
}
