package com.programmers.lime.domains.sse;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class ConcreteAlarmSubject implements IAlarmSubject {

	// 구독자들을 담아서 관리함
	// public final Map<Long, IAlarmObserver> observers = new ConcurrentHashMap<>();

	@Override
	public void registerObserver(
		final Long receiverId,
		final IAlarmObserver o
	) {
		if (observers.get(receiverId) == null) {
			observers.put(receiverId, o);
		}
		o.create(receiverId);
	}

	@Override
	public void notifyObserver(final SsePayload ssePayload) {
		IAlarmObserver o = observers.get(ssePayload.receiverId());
		o.send(ssePayload);
	}
}
