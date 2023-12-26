package com.programmers.bucketback.domains.comment.application;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CommentEventAction {
	COMMENT_CREATE("댓글 생성.");

	private final String description;
}
