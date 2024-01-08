package com.programmers.lime.domains.sse;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class SseEventListener {

	private final SseEmitters sseEmitters;

	@EventListener
	public void sendSseEmitter(final SsePayload ssePayload) {
		sseEmitters.send(
			ssePayload.receiverId(),
			ssePayload.data()
		);
	}
}
