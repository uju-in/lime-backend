package com.programmers.lime.redis.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.programmers.lime.redis.chat.lua.ChatDQueueScriptManager;
import com.programmers.lime.redis.chat.model.ChatInfoWithMemberCache;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatBufferManager {

	private static final String CHAT_BUFFER_KEY = "CHAT_BUFFER_CACHE";
	private final RedisTemplate<String, Object> redisTemplate;
	private final ChatDQueueScriptManager chatDQueueScriptManager;

	public List<ChatInfoWithMemberCache> read(final int size) {
		List<Object> result = chatDQueueScriptManager.executeDQueueScript(CHAT_BUFFER_KEY, size);

		if (Objects.isNull(result) || result.isEmpty()) {
			return List.of();
		}

		List<ChatInfoWithMemberCache> infoWithMemberCaches = new ArrayList<>();
		for (Object item : result) {
			ChatInfoWithMemberCache cache = (ChatInfoWithMemberCache)redisTemplate.getValueSerializer()
				.deserialize((byte[])item);
			infoWithMemberCaches.add(cache);
		}

		return infoWithMemberCaches;
	}

	public void append(final ChatInfoWithMemberCache chatInfoWithMemberCache) {
		redisTemplate.opsForList().leftPush(CHAT_BUFFER_KEY, chatInfoWithMemberCache);
	}
}
