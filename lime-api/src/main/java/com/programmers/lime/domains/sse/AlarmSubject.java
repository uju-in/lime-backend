package com.programmers.lime.domains.sse;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface AlarmSubject {

	SseEmitter create(Long receiverId);

	void send(SsePayload ssePayload);
}
