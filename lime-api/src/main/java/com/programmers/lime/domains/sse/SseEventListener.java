package com.programmers.lime.domains.sse;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class SseEventListener {

	private final SseEmitters sseEmitters;

	@TransactionalEventListener
	@Async
	public void sendSseEmitter(final SsePayload ssePayload) {
		sseEmitters.send(
			ssePayload.receiverId(),
			ssePayload.data()
		);
	}
}
