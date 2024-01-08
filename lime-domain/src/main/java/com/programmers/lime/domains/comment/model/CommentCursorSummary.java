package com.programmers.lime.domains.comment.model;

import java.util.List;

import com.programmers.lime.domains.comment.repository.CommentSummary;

public record CommentCursorSummary(
	String nextCursorId,
	int summaryCount,
	List<CommentSummary> summaries
) {
}

