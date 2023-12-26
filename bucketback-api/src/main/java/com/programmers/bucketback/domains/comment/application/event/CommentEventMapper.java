package com.programmers.bucketback.domains.comment.application.event;

import com.programmers.bucketback.domains.sse.SsePayload;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentEventMapper {

	public static SsePayload toSsePayload(
		String alarmMessage,
		CommentCreateEvent commentCreateEvent
	) {
		return SsePayload.builder()
			.receiverId(commentCreateEvent.receiverId())
			.data(commentCreateEvent.toMap(alarmMessage))
			.build();
	}
}