package com.programmers.lime.domains.sse;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface AlarmManager {

	SseEmitter registerManager(Long receiverId, AlarmSubject o);

	void notifyManager(SsePayload ssePayload);
}
