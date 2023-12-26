package com.programmers.bucketback.domains.comment.application.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.sse.SsePayload;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentEventService {

	private final ApplicationEventPublisher applicationEventPublisher;

	public void notifyCommentCreate(final CommentCreateEvent commentCreateEvent) {
		String alarmMessage = CommentEventType.COMMENT_CREATE.getDescription();
		SsePayload ssePayload = CommentEventMapper.toSsePayload(alarmMessage, commentCreateEvent);

		applicationEventPublisher.publishEvent(ssePayload);
	}
}
