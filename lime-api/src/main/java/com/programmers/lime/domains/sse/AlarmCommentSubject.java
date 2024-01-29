package com.programmers.lime.domains.sse;

import static org.springframework.web.servlet.mvc.method.annotation.SseEmitter.*;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AlarmCommentSubject implements AlarmSubject {
	private static final Long DEFAULT_TIMEOUT = 6000 * 1000L; // 60초

	private final Map<Long, SseEmitter> emitter = new ConcurrentHashMap<>();

	@Override
	public SseEmitter create(final Long receiverId) {
		SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);
		UUID uuid = UUID.randomUUID();

		this.emitter.put(uuid.getMostSignificantBits(), sseEmitter);

		sseEmitter.onTimeout(sseEmitter::complete);
		sseEmitter.onCompletion(
			() -> {
				log.info("SSE 연결이 종료되었습니다. userId : {}", receiverId);
				this.emitter.remove(uuid.getMostSignificantBits());
			}
		);
		sseEmitter.onError(
			(e) -> {
				log.error("오류로 인해 SSE 연결이 종료되었습니다. userId : {}", receiverId);
				this.emitter.remove(uuid.getMostSignificantBits());
			}
		);
		return sseEmitter;
	}

	@Override
	public void send(
		final SsePayload payload
	) {
		if (this.emitter == null) {
			log.error("SSE를 구독하지 않은 유저입니다. userId : {}", payload.receiverId());
			return;
		}

		this.emitter.forEach(
			(id, emitter) -> {
				try {
					emitter.send(payload.data());
					log.info("알람을 보냈습니다. userId : {}", payload.receiverId());
				} catch (Exception e) {
					log.error("알람을 보내는 과정에서 오류가 발생했습니다.");
				}
			}
		);
	}

	@Scheduled(fixedRate = 45 * 1000) // 45초 간격
	public void sendHeartBeat() {
		this.emitter.forEach((emitterId, emitter) -> {
			try {
				emitter.send(event().comment("heartbeat"));
				log.info("heartbeat을 보냈습니다.");
			} catch (IOException e) {
				emitter.complete();
			}
		});
	}
}
