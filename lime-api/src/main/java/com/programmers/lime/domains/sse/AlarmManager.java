package com.programmers.lime.domains.sse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface AlarmManager {

	Map<Long, AlarmSubject> observers = new ConcurrentHashMap<>();

	SseEmitter registerObserver(Long receiverId, AlarmSubject o);

	void notifyObserver(SsePayload ssePayload);
}
