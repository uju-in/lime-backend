package com.programmers.bucketback.domains.comment.application.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

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
