package com.programmers.bucketback.domains.sse;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

class SseEmittersTest {

	SseEmitters sseEmitters = new SseEmitters();
	Long memberId = 1L;
	Long defaultTimeOut = 30 * 60 * 1000L;

	@Test
	@DisplayName("새로운 Emitters를 추가에 성공한다.")
	public void successAddEmitter() throws Exception {
		//when
		SseEmitter sseEmitter = sseEmitters.add(memberId);

		//then
		assertThat(sseEmitter.getTimeout()).isEqualTo(defaultTimeOut);
	}

	@Test
	@DisplayName("Emitters에 있는 정보를 가져올 수 있다.")
	public void successGetEmitter() throws Exception {
		//when
		sseEmitters.add(memberId);
		SseEmitter sseEmitter = sseEmitters.get(memberId);

		//then
		assertThat(sseEmitter).isNotNull();
	}

	@Test
	@DisplayName("Emitters에 저장된 정보가 없다.")
	public void failGetEmitter() throws Exception {
		//when
		SseEmitter sseEmitter = sseEmitters.get(memberId);

		//then
		assertThat(sseEmitter).isNull();
	}
}