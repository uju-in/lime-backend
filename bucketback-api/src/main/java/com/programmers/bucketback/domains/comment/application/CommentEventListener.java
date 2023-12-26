package com.programmers.bucketback.domains.comment.application;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.comment.application.dto.response.CommentCreateEvent;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CommentEventListener {

	private final CommentEventService commentEventService;

	@EventListener
	public void notifyCommentCreate(final CommentCreateEvent commentCreateEvent) {
		commentEventService.notifyCommentCreate(commentCreateEvent);
	}
}
