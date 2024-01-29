// package com.programmers.lime.domains.sse;
//
// import java.io.IOException;
// import java.util.Map;
// import java.util.concurrent.ConcurrentHashMap;
// import java.util.UUID;
//
// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Component;
// import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
//
// import lombok.extern.slf4j.Slf4j;
//
// @Slf4j
// @Component
// public class SseEmitters {
//
// 	private static final Long DEFAULT_TIMEOUT = 60 * 1000L; // 60초
// 	private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
//
// 	public SseEmitter add(final Long receiverId) {
// 		SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);
//
// 		//register의 동작
// 		UUID uuid = UUID.randomUUID();
//
// 		if(emitters.get(receiverId) == null) {
// 			emitters.put(receiverId, new ConcurrentHashMap<>());
// 		}
//
// 		Map<Long, SseEmitter> longSseEmitterMap = this.emitters.get(receiverId);
// 		longSseEmitterMap.put(uuid.getMostSignificantBits(), sseEmitter);
//
// 		sseEmitter.onTimeout(sseEmitter::complete);
// 		sseEmitter.onCompletion(
// 			() -> {
// 				log.error("SSE 연결이 종료되었습니다. userId : {}", receiverId);
// 				Map<Long, SseEmitter> emitterMap = this.emitters.get(receiverId);
// 				emitterMap.remove(uuid.getMostSignificantBits());
// 			}
// 		);
// 		sseEmitter.onError(
// 			(e) -> {
// 				log.error("SSE 연결이 종료되었습니다. userId : {}", receiverId);
// 				Map<Long, SseEmitter> emitterMap = this.emitters.get(receiverId);
// 				emitterMap.remove(uuid.getMostSignificantBits());
// 			}
// 		);
//
// 		return sseEmitter;
// 	}
//
// 	//subject 명령에 의해 동작됨
// 	public void send(
// 		final Long receiverId,
// 		final Object data
// 	) {
// 		Map<Long, SseEmitter> sseEmitterMap = emitters.get(receiverId);
// 		if (sseEmitterMap == null) {
// 			log.warn("SSE를 구독하지 않은 유저입니다. userId : {}", receiverId);
// 			return;
// 		}
//
// 		sseEmitterMap.forEach(
// 			(id, emitter) -> {
// 				try {
// 					emitter.send(data);
// 					log.info("알람을 보냈습니다. userId : {}", receiverId);
// 				} catch (Exception e) {
// 					log.warn("알람을 보내는 과정에서 오류가 발생했습니다.");
// 				}
// 			}
// 		);
// 	}
//
// 	@Scheduled(fixedRate = 45 * 1000) // 45초 간격
// 	public void sendHeartbeat() {
// 		emitters.forEach((id, sseEmitterMap) -> {
// 			sseEmitterMap.forEach((emitterId, emitter) -> {
// 				try {
// 					emitter.send(SseEmitter.event().comment("heartbeat"));
// 					log.info("heartbeat을 보냈습니다. userId : {}", id);
// 				} catch (IOException e) {
// 					emitter.complete();
// 				}
// 			});
// 		});
// 	}
// }
