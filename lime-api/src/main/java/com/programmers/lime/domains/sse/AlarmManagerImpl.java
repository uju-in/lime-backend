package com.programmers.lime.domains.sse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
public class AlarmManagerImpl implements AlarmManager {

	private final Map<Long, AlarmSubject> observers = new ConcurrentHashMap<>();

	@Override
	public SseEmitter registerManager(
		final Long receiverId,
		final AlarmSubject o
	) {
		if (observers.get(receiverId) == null) {
			observers.put(receiverId, o);
		}
		return o.create(receiverId);
	}

	@Override
	public void notifyManager(final SsePayload ssePayload) {
		AlarmSubject o = observers.get(ssePayload.receiverId());
		o.send(ssePayload);
	}
}
