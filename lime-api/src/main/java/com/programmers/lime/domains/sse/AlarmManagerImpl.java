package com.programmers.lime.domains.sse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
public class AlarmManagerImpl implements AlarmManager {

	@Override
	public SseEmitter registerObserver(
		final Long receiverId,
		final AlarmSubject o
	) {
		if (observers.get(receiverId) == null) {
			observers.put(receiverId, o);
		}
		return o.create(receiverId);
	}

	@Override
	public void notifyObserver(final SsePayload ssePayload) {
		AlarmSubject o = observers.get(ssePayload.receiverId());
		o.send(ssePayload);
	}
}
