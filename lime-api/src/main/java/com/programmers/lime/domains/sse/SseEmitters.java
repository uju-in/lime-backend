package com.programmers.lime.domains.sse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SseEmitters {

	private static final Long DEFAULT_TIMEOUT = 30 * 60 * 1000L; // 60분
	private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

	public SseEmitter add(final Long receiverId) {
		SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);

		this.emitters.put(receiverId, sseEmitter);
		sseEmitter.onTimeout(sseEmitter::complete);
		sseEmitter.onCompletion(() -> this.emitters.remove(receiverId));
		sseEmitter.onError((e) -> this.emitters.remove(receiverId));

		return sseEmitter;
	}

	public void send(
		final Long receiverId,
		final Object data
	) {
		SseEmitter sseEmitter = emitters.get(receiverId);
		if (sseEmitter == null) {
			log.warn("SSE를 구독하지 않은 유저입니다. userId : {}", receiverId);
			return;
		}

		try {
			sseEmitter.send(data);
			log.info("알람을 보냈습니다. userId : {}", receiverId);
		} catch (Exception e) {
			log.warn("알람을 보내는 과정에서 오류가 발생했습니다.");
		}
	}
}
