package com.programmers.lime.redis.chat;

import java.util.List;

import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.programmers.lime.redis.chat.model.LLReadScript;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatLLScriptManager {

	private final RedisTemplate<String, Object> redisTemplate;

	private final LLReadScript llReadScript;
	private String llScriptSha1;

	private static boolean isNoscript(final RedisSystemException e) {
		return e.getCause().getMessage().contains("NOSCRIPT");
	}

	@PostConstruct
	public void initLLSha1() {
		byte[] llBytes = llReadScript.getReadByLinkedListScriptBytes();
		llScriptSha1 = redisTemplate.execute((RedisConnection connection) ->
			connection.scriptLoad(llBytes)
		);
	}

	public List<Object> executeLLScript(final Long roomId, final String currCursorId, final int size) {
		try {
			return executeEvalSha(roomId, currCursorId, size);
		} catch (RedisSystemException e) {
			if (isNoscript(e)) {
				initLLSha1();
				return executeEvalSha(roomId, currCursorId, size);
			}
			throw e;
		}
	}

	private List<Object> executeEvalSha(final Long roomId, final String currCursorId, final int size) {
		return redisTemplate.execute(
			connection -> connection.evalSha(llScriptSha1.getBytes(), ReturnType.MULTI, 0,
				roomId.toString().getBytes(), currCursorId.getBytes(), String.valueOf(size).getBytes()), true
		);
	}
}
