package com.programmers.bucketback.domains.comment.application;

import java.util.List;

import com.programmers.bucketback.domains.comment.repository.CommentSummary;

public record CommentCursorSummary(
	String nextCursorId,
	int summaryCount,
	List<CommentSummary> summaries
) {
}

