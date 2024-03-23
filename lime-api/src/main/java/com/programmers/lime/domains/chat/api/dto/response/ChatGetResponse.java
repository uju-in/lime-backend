package com.programmers.lime.domains.chat.api.dto.response;

import java.util.List;

import com.programmers.lime.domains.chat.application.dto.response.ChatGetServiceResponse;
import com.programmers.lime.domains.chat.model.ChatInfoWithMember;

public record ChatGetResponse(
	List<ChatInfoWithMember> chatInfoWithMembers
) {
	public static ChatGetResponse from(final ChatGetServiceResponse chatGetServiceResponse) {
		return new ChatGetResponse(chatGetServiceResponse.chatInfoWithMembers());
	}
}
