package com.programmers.lime.redis.chat;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

import com.programmers.lime.redis.chat.model.ChatSessionInfo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatSessionRedisManager {
	private final RedisTemplate<String, Object> redisTemplate;
	private final static String SESSION_PREFIX = "chatSession:";
	private final static String MEMBER_ROOM_PREFIX = "memberRoom:";

	public void saveSession(String sessionId, ChatSessionInfo chatSessionInfo) {
		redisTemplate.opsForValue().set(SESSION_PREFIX + sessionId, chatSessionInfo);
		redisTemplate.opsForSet().add(
			getKey(chatSessionInfo),
			sessionId
		);
	}

	public ChatSessionInfo getSessionInfo(String sessionId) {
		return (ChatSessionInfo) redisTemplate.opsForValue().get(SESSION_PREFIX + sessionId);
	}

	public Set<String> getSessionIdsByMemberAndRoom(Long memberId, Long roomId) {
		Set<Object> rawSet = redisTemplate.opsForSet().members(MEMBER_ROOM_PREFIX + roomId + ":" + memberId);
		return rawSet.stream().map(Object::toString).collect(Collectors.toSet());
	}

	public void deleteSession(String sessionId) {
		ChatSessionInfo chatSessionInfo = getSessionInfo(sessionId);
		if (chatSessionInfo != null) {
			redisTemplate.delete(SESSION_PREFIX + sessionId);
			redisTemplate.opsForSet().remove(
				getKey(chatSessionInfo),
				sessionId
			);
		}
	}

	private static String getKey(final ChatSessionInfo chatSessionInfo) {
		return MEMBER_ROOM_PREFIX + chatSessionInfo.roomId() + ":" + chatSessionInfo.memberId();
	}
}
