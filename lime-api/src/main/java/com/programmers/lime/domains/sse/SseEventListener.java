package com.programmers.lime.domains.sse;

import java.util.Map;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class SseEventListener {

	private final IAlarmSubject iAlarmSubject;

	@TransactionalEventListener
	@Async
	public void sendSseEmitter(final SsePayload ssePayload) {
		iAlarmSubject.notifyObserver(ssePayload);
	}
}
