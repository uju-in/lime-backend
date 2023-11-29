package com.programmers.bucketback.domains.comment.application.dto.response;

import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.domains.comment.repository.CommentSummary;

public record CommentsGetServiceResponse(
	CursorSummary<CommentSummary> commentSummaries,
	int totalCommentCount
) {
	public static CommentsGetServiceResponse of(
		final CursorSummary<CommentSummary> commentSummaries,
		final int totalCommentCount
	) {
		return new CommentsGetServiceResponse(commentSummaries, totalCommentCount);
	}
}
