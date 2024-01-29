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
	private static final String DUMMY_DATA = "connected";
	private final ConcreteAlarmSubject concreteAlarmSubject;
	private final ObserverComment observerComment;

	public void subscribe() {
		Long receiverId = SecurityUtils.getCurrentMemberId();

		//observer관리자 생성
		//observer 등록
		concreteAlarmSubject.registerObserver(receiverId, observerComment);

		//최초 연결 시점에는 더미 데이터 전송
		concreteAlarmSubject.notifyObserver(new SsePayload("dummy", receiverId, Map.of("dummy", DUMMY_DATA)));

	}
}
