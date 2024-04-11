package com.programmers.lime.global.event.chat;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.programmers.lime.domains.chat.model.ChatInfoWithMember;
import com.programmers.lime.redis.chat.publisher.IChatPublisher;
import com.programmers.lime.redis.chat.model.ChatInfoWithMemberCache;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatSendMessageEventListener {

	private final IChatPublisher chatPublisher;

	@Async
	@EventListener
	public void sendMessage(final ChatSendMessageEvent event) {

		ChatInfoWithMemberCache chatInfoWithMemberCache = chatInfoWithMember(
			event.chatInfoWithMember(),
			event.destination()
		);

		chatPublisher.sendMessage("sub-chat", chatInfoWithMemberCache);
	}

	public ChatInfoWithMemberCache chatInfoWithMember(final ChatInfoWithMember chatInfoWithMember,
		final String destination) {
		return ChatInfoWithMemberCache.builder()
			.chatId(chatInfoWithMember.chatId())
			.chatRoomId(chatInfoWithMember.chatRoomId())
			.memberId(chatInfoWithMember.memberId())
			.nickname(chatInfoWithMember.nickname())
			.profileImage(chatInfoWithMember.profileImage())
			.message(chatInfoWithMember.message())
			.sendAt(chatInfoWithMember.sendAt().toString())
			.chatType(chatInfoWithMember.chatType().name())
			.destination(destination)
			.build();
	}
}
