package com.programmers.lime.domains.sse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface IAlarmSubject {

	Map<Long, IAlarmObserver> observers = new ConcurrentHashMap<>();

	void registerObserver(Long receiverId,IAlarmObserver o);
	void notifyObserver(SsePayload ssePayload);
}
