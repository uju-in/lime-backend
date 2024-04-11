package com.programmers.lime.redis.chat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.programmers.lime.redis.chat.model.CacheKeyAndStartIdxPair;
import com.programmers.lime.redis.chat.model.ChatCursorCache;
import com.programmers.lime.redis.chat.model.ChatCursorCacheResult;
import com.programmers.lime.redis.chat.model.ChatCursorCacheStatus;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatCursorCacheManager {

	private final static String CHAT_CURSOR_CACHE_KEY = "chatCursorCache";
	private final RedisTemplate<String, Object> redisTemplate;

	private static String getCacheKey(final Long roomId, final String cursorId) {
		return "cacheKey roomId:%s cursorId: %s".formatted(roomId, cursorId);
	}

	private static String getCursorKey(final Long roomId, final String cursorId) {
		return "roomId:%s cursorId: %s".formatted(roomId, cursorId);
	}

	private static ChatCursorCacheResult getSuccessResult(final List<ChatCursorCache> chatCursorCacheList) {
		return ChatCursorCacheResult.builder()
			.chatCursorCacheList(chatCursorCacheList)
			.chatCursorCacheStatus(ChatCursorCacheStatus.SUCCESS)
			.build();
	}

	private static ChatCursorCacheResult getFailResult() {
		return ChatCursorCacheResult.builder()
			.chatCursorCacheList(List.of())
			.chatCursorCacheStatus(ChatCursorCacheStatus.FAIL)
			.build();
	}

	public void saveChatSummariesPipelined(
		final String rootCursorId,
		final Long roomId,
		final List<ChatCursorCache> dataList
	) {
		redisTemplate.executePipelined((RedisCallback<Object>)connection -> {
			String cacheKey = getCacheKey(roomId, rootCursorId);
			redisTemplate.opsForList().rightPushAll(cacheKey, new ArrayList<>(dataList));

			for (int idx = 0; idx < dataList.size(); idx++) {
				String cursorKey = getCursorKey(roomId, dataList.get(idx).cursorId());
				redisTemplate.opsForHash()
					.put(CHAT_CURSOR_CACHE_KEY, cursorKey, new CacheKeyAndStartIdxPair(cacheKey, Double.valueOf(idx)));
			}

			redisTemplate.opsForHash().put(
				CHAT_CURSOR_CACHE_KEY,
				getCursorKey(roomId, rootCursorId),
				new CacheKeyAndStartIdxPair(cacheKey, -1.0)
			);
			return null;
		});
	}

	public ChatCursorCacheResult getChatSummaries(final Long roomId, final String cursorId, final int size) {
		CacheKeyAndStartIdxPair cacheKeyAndStartIdxPair = getCacheKeyScorePair(roomId, cursorId);
		if (cacheKeyAndStartIdxPair == null) {
			return getFailResult();
		}

		List<Object> chatSummarySet = getObjects(size, cacheKeyAndStartIdxPair);

		if (chatSummarySet == null || chatSummarySet.isEmpty()) {
			return getFailResult();
		}

		List<ChatCursorCache> chatCursorCacheList = processChatSummarySet(
			chatSummarySet,
			roomId,
			size
		);

		if (chatCursorCacheList.size() < size) {
			return getFailResult();
		}

		return getSuccessResult(chatCursorCacheList);
	}

	private CacheKeyAndStartIdxPair getCacheKeyScorePair(
		final Long roomId,
		final String cursorId
	) {
		String cursorKey = getCursorKey(roomId, cursorId);

		return (CacheKeyAndStartIdxPair)redisTemplate.opsForHash().get(CHAT_CURSOR_CACHE_KEY, cursorKey);
	}

	private List<ChatCursorCache> processChatSummarySet(
		final List<Object> chatSummarySet,
		final Long roomId,
		final int size
	) {
		List<ChatCursorCache> chatCursorCacheList = new ArrayList<>();
		for (Object chatSummary : chatSummarySet) {
			chatCursorCacheList.add((ChatCursorCache)chatSummary);
		}

		// 만약 요청한 size보다 적게 가져왔다면 다음 cursorId로 이동해서 가져온다.
		if (chatSummarySet.size() < size) {
			var lastChatSummary = chatCursorCacheList.get(chatCursorCacheList.size() - 1);

			// 다음 cursorId로 이동해서 가져온다.
			ChatCursorCacheResult cursorResitResult = getChatSummaries(
				roomId,
				lastChatSummary.cursorId(),
				size - chatSummarySet.size()
			);

			// 마지막 요소 부터 가져오기 때문에 마지막 요소를 제거한다.
			chatCursorCacheList.remove(chatCursorCacheList.size() - 1);

			// 새로 가져온 데이터를 추가한다.
			chatCursorCacheList.addAll(cursorResitResult.chatCursorCacheList());
		}

		return chatCursorCacheList;
	}

	private List<Object> getObjects(final int size, final CacheKeyAndStartIdxPair cacheKeyAndStartIdxPair) {
		long start = cacheKeyAndStartIdxPair.startIdx().longValue() + 1;
		long end = start + size;
		List<Object> chatSummarySet = redisTemplate.opsForList().range(cacheKeyAndStartIdxPair.cacheKey(), start, end);
		return chatSummarySet;
	}

}