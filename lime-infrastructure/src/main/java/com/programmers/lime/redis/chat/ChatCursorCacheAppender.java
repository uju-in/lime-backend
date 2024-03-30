package com.programmers.lime.redis.chat;

import static com.programmers.lime.redis.chat.ChatCursorCacheUtil.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.programmers.lime.redis.chat.model.ChatCursorCache;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatCursorCacheAppender {

	private final RedisTemplate<String, Object> redisTemplate;

	private String lastCursorKey = "";

	public void append(
		final Long roomId,
		final String requestStartCursorId,
		final List<ChatCursorCache> chatCursorCaches
	) {
		var hash = redisTemplate.opsForHash();
		var cursorMap = new HashMap<String, ChatCursorCache>();

		String roomKey = getRoomKey(roomId);

		// 요청 커서 Id는 chatCursorCaches에 없기 때문에 따로 처리
		// 요청 커서의 다음 커서 Id가 없다면 chatCursorCaches의 첫 번째 커서 Id로 설정
		if (chatCursorCaches.size() > 0) {
			appendCursorCacheIfAbsent(roomId, requestStartCursorId, chatCursorCaches.get(0), cursorMap);
		}

		for (ChatCursorCache chatCursorCache : chatCursorCaches) {
			String cursorKey = getCursorKey(chatCursorCache.cursorId());

			// lastCursorKey는 init할 때 초기화 되어 있기 때문에 비교를 통해 중복 저장 방지
			if (cursorKey.equals(lastCursorKey)) {
				continue;
			}

			cursorMap.put(cursorKey, chatCursorCache);
		}

		hash.putAll(roomKey, cursorMap);
	}

	private void appendCursorCacheIfAbsent(
		final Long roomId,
		final String targetCursorId,
		final ChatCursorCache nextChatCursorCache,
		final Map<String, ChatCursorCache> cursorMap
	) {
		var hash = redisTemplate.opsForHash();
		String roomKey = getRoomKey(roomId);
		ChatCursorCache targetCursorData = (ChatCursorCache)hash.get(roomKey, getCursorKey(targetCursorId));

		if (targetCursorData == null) {
			targetCursorData = ChatCursorCache.builder()
				.cursorId(targetCursorId)
				.build();
		}

		if (targetCursorData.nextCursorId() != null) {
			return;
		}

		ChatCursorCache newChatCursorData = ChatCursorCache.newNextCursorId(targetCursorData,
			nextChatCursorCache.cursorId());

		cursorMap.put(getCursorKey(targetCursorId), newChatCursorData);
	}

	public void appendHeadNext(
		final Long roomId,
		final ChatCursorCache firstChatCursorCache
	) {
		var hash = redisTemplate.opsForHash();

		ChatCursorCache firstCursorData = ChatCursorCache.builder()
			.cursorId(HEAD_CURSOR_ID)
			.nextCursorId(firstChatCursorCache.cursorId())
			.build();

		String roomKey = getRoomKey(roomId);
		String headCursorKey = getCursorKey(HEAD_CURSOR_ID);
		hash.put(roomKey, headCursorKey, firstCursorData);
	}

	public void appendLastChatNext(
		final Long roomId,
		final ChatCursorCache lastChatCursorCache
	) {
		var hash = redisTemplate.opsForHash();

		ChatCursorCache lastCursorData = ChatCursorCache.newNextCursorId(lastChatCursorCache, TAIL_CURSOR_ID);

		String roomKey = getRoomKey(roomId);
		String lastCursorKey = getCursorKey(lastChatCursorCache.cursorId());
		hash.put(roomKey, lastCursorKey, lastCursorData);

		this.lastCursorKey = getCursorKey(lastChatCursorCache.cursorId());
	}
}
