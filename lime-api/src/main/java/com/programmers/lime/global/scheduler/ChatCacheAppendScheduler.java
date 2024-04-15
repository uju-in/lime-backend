package com.programmers.lime.global.scheduler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.programmers.lime.domains.chat.implementation.ChatAppender;
import com.programmers.lime.domains.chat.model.ChatInfo;
import com.programmers.lime.domains.chat.model.ChatType;
import com.programmers.lime.redis.chat.ChatBufferManager;
import com.programmers.lime.redis.chat.model.ChatInfoWithMemberCache;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatCacheAppendScheduler {

	private final ChatBufferManager chatBufferManager;

	private final ChatAppender chatAppender;

	@Scheduled(fixedRate = 10000)
	public void appendCache() {
		List<ChatInfoWithMemberCache> chatInfoWithMemberCaches = chatBufferManager.read(1000);

		if (chatInfoWithMemberCaches.isEmpty()) {
			return;
		}

		chatAppender.appendChats(getChatInfos(chatInfoWithMemberCaches));
	}

	public List<ChatInfo> getChatInfos(final List<ChatInfoWithMemberCache> chatInfoWithMemberCaches) {
		return chatInfoWithMemberCaches.stream().map(s ->
			ChatInfo.builder()
				.chatId(s.chatId())
				.chatRoomId(s.chatRoomId())
				.memberId(s.memberId())
				.message(s.message())
				.sendAt(toLocalDateTime(s.sendAt()))
				.chatType(ChatType.valueOf(s.chatType()))
				.build()
		).toList();
	}

	private LocalDateTime toLocalDateTime(final String sendAt) {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		return LocalDateTime.parse(sendAt, formatter);
	}
}
