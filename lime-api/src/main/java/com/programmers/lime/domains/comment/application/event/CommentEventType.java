package com.programmers.lime.domains.comment.application.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommentEventType {
	COMMENT_CREATE("댓글 생성"),
	COMMENT_ADOPTED("댓글 채택");

	private final String description;
}
