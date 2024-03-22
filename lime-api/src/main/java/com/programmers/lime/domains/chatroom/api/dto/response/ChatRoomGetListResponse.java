package com.programmers.lime.domains.chatroom.api.dto.response;

import java.util.List;

import com.programmers.lime.domains.chatroom.application.dto.response.ChatRoomGetServiceListResponse;
import com.programmers.lime.domains.chatroom.model.ChatRoomInfo;

public record ChatRoomGetListResponse(
	List<ChatRoomInfo> chatRoomInfos
) {
	public static ChatRoomGetListResponse from(final ChatRoomGetServiceListResponse chatRoomGetServiceListResponse) {
		return new ChatRoomGetListResponse(
			chatRoomGetServiceListResponse.chatRoomInfos()
		);
	}
}
