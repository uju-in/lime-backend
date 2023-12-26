package com.programmers.bucketback.domains.comment.application;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.comment.application.dto.response.CommentCreateEvent;
import com.programmers.bucketback.domains.sse.SsePayload;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentEventService {

	private final ApplicationEventPublisher applicationEventPublisher;

	public void notifyCommentCreate(final CommentCreateEvent commentCreateEvent) {
		String alarmType = CommentEventAction.COMMENT_CREATE.toString();
		SsePayload ssePayload = CommentEventMapper.toSsePayload(alarmType, commentCreateEvent);
		applicationEventPublisher.publishEvent(ssePayload);
	}
}
