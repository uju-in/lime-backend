package com.programmers.lime.domains.sse;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.programmers.lime.global.config.security.SecurityUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SseService {
	private static final String DUMMY_DATA = "connected";
	private final SseEmitters sseEmitters;

	public SseEmitter subscribe() {
		Long receiverId = SecurityUtils.getCurrentMemberId();
		SseEmitter sseEmitter = sseEmitters.add(receiverId);

		//최초 연결 시점에는 더미 데이터 전송
		sseEmitters.send(receiverId, DUMMY_DATA);

		return sseEmitter;
	}
}
