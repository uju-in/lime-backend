package com.programmers.lime.redis.chat;

import static com.programmers.lime.redis.chat.ChatCursorCacheUtil.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.programmers.lime.redis.chat.model.ChatCursorCache;
import com.programmers.lime.redis.chat.model.ChatCursorCacheResult;
import com.programmers.lime.redis.chat.model.ChatCursorCacheStatus;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatCursorCacheReader {

	private final RedisTemplate<String, Object> redisTemplate;

	public ChatCursorCacheResult readByCursor(
		final Long roomId,
		final String cursorId,
		final int size
	) {
		String requestStartCursorId = cursorId == null ? HEAD_CURSOR_ID : cursorId;
		String nextCursorId = readNextCursorId(roomId, requestStartCursorId);

		// 요청 받은 커서 Id 바로 다음 Id가 캐시에 없는 경우
		if (Objects.isNull(nextCursorId)) {
			return getFailResult();
		}

		// 요청 받은 커서 Id의 다음이 바로 tail인 경우
		if (nextCursorId.equals(TAIL_CURSOR_ID)) {
			return getSuccessResult();
		}

		List<ChatCursorCache> cursorCaches = readCursorCachesByLua(roomId, nextCursorId, size);

		// (커서가 캐시에 없는 경우) || (요청한 크기보다 작은데 마지막 커서가 tail 이 아닌 경우)
		if (cursorCaches.isEmpty() || (cursorCaches.size() < size && !isTail(cursorCaches))) {
			return getFailResult();
		}

		// 성공적으로 처리된 경우, 마지막 커서 ID와 함께 결과 반환
		return new ChatCursorCacheResult(
			cursorCaches,
			ChatCursorCacheStatus.SUCCESS,
			cursorCaches.get(cursorCaches.size() - 1).cursorId()
		);
	}

	private String readNextCursorId(final Long roomId, final String cursorId) {

		if (Objects.isNull(cursorId)) {
			return null;
		}

		var hash = redisTemplate.opsForHash();
		final String roomKey = getRoomKey(roomId);

		String cursorKey = getCursorKey(cursorId);
		ChatCursorCache currChatCursorCache = (ChatCursorCache)hash.get(roomKey, cursorKey);

		return Objects.isNull(currChatCursorCache) ? null : currChatCursorCache.nextCursorId();
	}

	/*
	- roomId에 해당하는 채팅 캐시를 가져옴
	 */
	private List<ChatCursorCache> readCursorCaches(
		final Long roomId,
		final String currCursorId,
		final int size
	) {
		List<ChatCursorCache> cursorCaches = new ArrayList<>();
		var hash = redisTemplate.opsForHash();

		final String roomKey = getRoomKey(roomId);
		String currCursorKey = getCursorKey(currCursorId);

		for (int idx = 0; idx < size; idx++) {
			ChatCursorCache currChatCursorCache = (ChatCursorCache)hash.get(roomKey, currCursorKey);

			if (Objects.isNull(currChatCursorCache) || Objects.isNull(currChatCursorCache.cursorId())) {
				break;
			}

			currCursorKey = getCursorKey(currChatCursorCache.nextCursorId());
			cursorCaches.add(currChatCursorCache);
		}

		return cursorCaches;
	}

	private boolean isTail(final List<ChatCursorCache> cursorCaches) {
		var lastOne = cursorCaches.get(cursorCaches.size() - 1);
		if (lastOne.nextCursorId() == null) {
			return false;
		}

		return lastOne.nextCursorId().equals(TAIL_CURSOR_ID);
	}

	private List<ChatCursorCache> readCursorCachesByLua(
		final Long roomId,
		final String currCursorId,
		final int size
	) {
		String linkedListScript = ChatLuaScriptManager.getReadByLinkedListScript();

		// 스크립트 실행
		List<Object> result = redisTemplate.execute(
			connection -> connection.eval(linkedListScript.getBytes(), ReturnType.MULTI, 0,
				roomId.toString().getBytes(), currCursorId.getBytes(), String.valueOf(size).getBytes()), true);

		if (result == null || result.isEmpty()) {
			return List.of();
		}

		List<ChatCursorCache> chatCursorCaches = new ArrayList<>();
		for (Object item : result) {
			ChatCursorCache cache = (ChatCursorCache)redisTemplate.getValueSerializer().deserialize((byte[])item);
			chatCursorCaches.add(cache);
		}

		return chatCursorCaches;
	}
}
