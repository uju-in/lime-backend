package com.programmers.lime.domains.sse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface AlarmSubject {
	Map<Long, SseEmitter> emitter = new ConcurrentHashMap<>();

	SseEmitter create(Long receiverId);
	void send(SsePayload ssePayload);
}
