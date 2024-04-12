package com.programmers.lime.global.config.chat.message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.programmers.lime.domains.chat.model.ChatInfoWithMember;
import com.programmers.lime.domains.chat.model.ChatType;
import com.programmers.lime.redis.chat.listener.IChatListener;
import com.programmers.lime.redis.chat.model.ChatInfoWithMemberCache;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SimpleChatListener implements IChatListener {

	private final SimpMessagingTemplate simpMessagingTemplate;

	@Override
	public void sendMessage(final ChatInfoWithMemberCache chatInfoWithMemberCache) {

		if (chatInfoWithMemberCache == null) {
			throw new IllegalStateException("Deserialized message is null. Message deserialization failed.");
		}

		simpMessagingTemplate.convertAndSend(chatInfoWithMemberCache.destination(),
			toChatInfoWithMember(chatInfoWithMemberCache));
	}

	private ChatInfoWithMember toChatInfoWithMember(final ChatInfoWithMemberCache chatInfoWithMemberCache) {
		return ChatInfoWithMember.builder()
			.chatId(chatInfoWithMemberCache.chatId())
			.chatRoomId(chatInfoWithMemberCache.chatRoomId())
			.memberId(chatInfoWithMemberCache.memberId())
			.nickname(chatInfoWithMemberCache.nickname())
			.profileImage(chatInfoWithMemberCache.profileImage())
			.message(chatInfoWithMemberCache.message())
			.sendAt(toLocalDateTime(chatInfoWithMemberCache.sendAt()))
			.chatType(ChatType.valueOf(chatInfoWithMemberCache.chatType()))
			.build();
	}

	private LocalDateTime toLocalDateTime(final String sendAt) {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		return LocalDateTime.parse(sendAt, formatter);
	}
}
