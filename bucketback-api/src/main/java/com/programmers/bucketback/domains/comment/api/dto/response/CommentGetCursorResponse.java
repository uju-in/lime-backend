package com.programmers.bucketback.domains.comment.api.dto.response;

import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.domains.comment.repository.CommentSummary;

public record CommentGetCursorResponse(
	String nextCursorId,
	int totalCount,
	CursorSummary<CommentSummary> comments
) {
	public static CommentGetCursorResponse from(final CursorSummary<CommentSummary> summaries) {
		return new CommentGetCursorResponse(
			summaries.nextCursorId(),
			summaries.summaryCount(),
			summaries
		);
	}
}

