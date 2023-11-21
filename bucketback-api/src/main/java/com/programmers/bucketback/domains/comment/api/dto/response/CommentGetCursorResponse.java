package com.programmers.bucketback.domains.comment.api.dto.response;

import java.util.List;

import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.domains.comment.repository.CommentSummary;

public record CommentGetCursorResponse(
	String nextCursorId,
	int totalCount,
	List<CommentSummary> comments
) {
	public static CommentGetCursorResponse from(final CursorSummary<CommentSummary> summary) {
		return new CommentGetCursorResponse(
			summary.nextCursorId(),
			summary.summaryCount(),
			summary.summaries()
		);
	}
}

