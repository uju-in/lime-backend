package com.programmers.lime.domains.sse;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.programmers.lime.global.config.security.SecurityUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SseService {
	private static final Map<String, Object> DUMMY_DATA = Map.of("dummy", "dummy");

	private final AlarmManager alarmManager;
	private final AlarmSubject alarmSubject;

	public SseEmitter subscribe() {
		Long receiverId = SecurityUtils.getCurrentMemberId();

		//observer 등록
		SseEmitter sseEmitter = alarmManager.registerManager(receiverId, alarmSubject);

		//최초 연결 시점에는 더미 데이터 전송
		alarmManager.notifyManager(new SsePayload("dummy", receiverId, DUMMY_DATA));

		return sseEmitter;

	}
}
