package com.programmers.lime.redis.chat.lua;

import java.util.List;

import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatDQueueScriptManager {

	private final RedisTemplate<String, Object> redisTemplate;

	private final DQueueReadScript dQueueReadScript;

	private String dQueueScriptSha1;

	private static boolean isNoscript(final RedisSystemException e) {
		return e.getCause().getMessage().contains("NOSCRIPT");
	}

	@PostConstruct
	public void initLLSha1() {
		byte[] llBytes = dQueueReadScript.getReadByLinkedListScriptBytes();
		dQueueScriptSha1 = redisTemplate.execute((RedisConnection connection) ->
			connection.scriptLoad(llBytes)
		);
	}

	public List<Object> executeDQueueScript(final String key, final int size) {
		try {
			return executeEvalSha(key, size);
		} catch (RedisSystemException e) {
			if (isNoscript(e)) {
				initLLSha1();
				return executeEvalSha(key, size);
			}
			throw e;
		}
	}

	private List<Object> executeEvalSha(final String key, final int size) {

		return redisTemplate.execute(
			connection -> connection.evalSha(
				dQueueScriptSha1.getBytes(), ReturnType.MULTI,
				1, key.getBytes(), String.valueOf(size).getBytes()), true
		);
	}
}
