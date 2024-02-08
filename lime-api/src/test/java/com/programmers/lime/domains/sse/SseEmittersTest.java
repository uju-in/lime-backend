// package com.programmers.lime.domains.sse;
//
// import static org.assertj.core.api.Assertions.*;
// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;
//
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.slf4j.Logger;
// import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
//
// class SseEmittersTest {
//
// 	SseEmitters sseEmitters = new SseEmitters();
// 	Long memberId = 1L;
// 	Long defaultTimeOut = 30 * 60 * 1000L;
// 	Logger mockLogger = mock(Logger.class);
//
// 	@Test
// 	@DisplayName("새로운 Emitters를 추가에 성공한다.")
// 	public void successAddEmitter() throws Exception {
// 		//when
// 		SseEmitter sseEmitter = sseEmitters.add(memberId);
//
// 		//then
// 		assertThat(sseEmitter.getTimeout()).isEqualTo(defaultTimeOut);
// 	}
//
// 	@Test
// 	@DisplayName("Emitters에 정보가 존재한다.")
// 	public void successGetEmitter() throws Exception {
// 		//when
// 		sseEmitters.add(memberId);
//
// 		//then
// 		verify(mockLogger, times(1));
// 		assertDoesNotThrow(() -> sseEmitters.send(memberId, null));
// 	}
//
// 	@Test
// 	@DisplayName("Emitters에 저장된 정보가 없다.")
// 	public void failGetEmitter() throws Exception {
// 		verify(mockLogger, times(1));
// 		assertDoesNotThrow(() -> sseEmitters.send(memberId, null));
// 	}
// }