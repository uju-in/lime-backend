package com.programmers.bucketback.domains.comment.application;

import com.programmers.bucketback.domains.comment.application.dto.response.CommentCreateEvent;
import com.programmers.bucketback.domains.sse.SsePayload;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentEventMapper {

	public static SsePayload toSsePayload(
		String alarmType,
		CommentCreateEvent commentCreateEvent
	) {
		return SsePayload.builder()
			.receiverId(commentCreateEvent.receiverId())
			.data(commentCreateEvent.toMap(alarmType))
			.build();
	}
}