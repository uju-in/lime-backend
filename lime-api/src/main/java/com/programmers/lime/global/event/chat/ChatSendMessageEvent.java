package com.programmers.lime.global.event.chat;

import com.programmers.lime.domains.chat.model.ChatInfoWithMember;

public record ChatSendMessageEvent(
	String destination,
	ChatInfoWithMember chatInfoWithMember
) {
}
