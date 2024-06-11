package com.programmers.lime.global.event.chat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.programmers.lime.domains.chat.implementation.ChatReader;
import com.programmers.lime.domains.chat.model.ChatSummary;
import com.programmers.lime.domains.chatroom.model.ChatRoomInfo;
import com.programmers.lime.redis.chat.ChatCursorCacheAppender;
import com.programmers.lime.redis.chat.ChatCursorCacheUtil;
import com.programmers.lime.redis.chat.model.ChatCursorCache;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatAppendEventListener {

	private final ChatCursorCacheAppender chatCursorCacheAppender;

	private final ChatReader chatReader;

	private static String getNextCursorId(
		final int requestSize,
		final List<ChatSummary> summaries,
		final int idx
	) {
		String nextCursorId = null;

		if (idx + 1 < summaries.size()) {
			nextCursorId = summaries.get(idx + 1).cursorId();
		}

		if (summaries.size() < requestSize && idx == summaries.size() - 1) {
			nextCursorId = ChatCursorCacheUtil.TAIL_CURSOR_ID;
		}

		return nextCursorId;
	}

	@Async
	@EventListener
	public void publishAppendCacheEvent(final ChatAppendCacheEvent chatAppendCacheEvent) {
		List<ChatSummary> summaries = chatAppendCacheEvent.summaries();
		List<ChatCursorCache> chatCursorCacheList = new ArrayList<>();
		for (int i = 0; i < summaries.size(); i++) {
			ChatSummary chatSummary = summaries.get(i);
			String nextCursorId = getNextCursorId(chatAppendCacheEvent.requestSize(), summaries, i);

			chatCursorCacheList.add(getChatSummaryCacheData(chatSummary, nextCursorId));
		}

		chatCursorCacheAppender.append(
			chatAppendCacheEvent.chatRoomId(),
			chatAppendCacheEvent.startCursorId(),
			chatCursorCacheList
		);
	}

	@Async
	@EventListener
	public void publishChatInitCacheEvent(final ChatInitCacheEvent chatInitCacheEvent) {
		List<ChatRoomInfo> chatRoomInfos = chatInitCacheEvent.chatRoomInfos();

		for (ChatRoomInfo chatRoomInfo : chatRoomInfos) {
			ChatSummary firstChat = chatReader.readFirstChat(chatRoomInfo.chatRoomId());
			ChatSummary lastChat = chatReader.readLastChat(chatRoomInfo.chatRoomId());

			if (firstChat == null || lastChat == null) {
				continue;
			}

			chatCursorCacheAppender.appendHeadNext(
				chatRoomInfo.chatRoomId(),
				getChatSummaryCacheData(firstChat, null)
			);

			chatCursorCacheAppender.appendLastChatNext(
				chatRoomInfo.chatRoomId(),
				getChatSummaryCacheData(lastChat, null)
			);
		}
	}

	private ChatCursorCache getChatSummaryCacheData(
		final ChatSummary chatSummary,
		final String nextCursorId
	) {
		return ChatCursorCache.builder()
			.cursorId(chatSummary.cursorId())
			.nextCursorId(nextCursorId)
			.chatId(chatSummary.chatId())
			.chatRoomId(chatSummary.chatRoomId())
			.memberId(chatSummary.memberId())
			.message(chatSummary.message())
			.sendAt(chatSummary.sendAt().toString())
			.chatType(chatSummary.chatType().name())
			.build();
	}
}
