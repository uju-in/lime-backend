package com.programmers.lime.domains.sse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface IAlarmObserver {
	Map<Long, SseEmitter> emitter = new ConcurrentHashMap<>();

	void create(Long receiverId);
	void send(SsePayload ssePayload);
}
