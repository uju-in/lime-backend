package com.programmers.lime.domains.chat.application.dto.response;

import java.util.List;

import com.programmers.lime.domains.chat.model.ChatInfoWithMember;

public record ChatGetServiceResponse(
	List<ChatInfoWithMember> chatInfoWithMembers
) {
}
