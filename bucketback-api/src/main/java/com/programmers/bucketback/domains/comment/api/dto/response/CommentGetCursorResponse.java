package com.programmers.bucketback.domains.comment.api.dto.response;

import java.util.List;

import com.programmers.bucketback.domains.comment.application.dto.response.CommentsGetServiceResponse;
import com.programmers.bucketback.domains.comment.repository.CommentSummary;

public record CommentGetCursorResponse(
	String nextCursorId,
	int totalCount,
	int totalCommentCount,
	List<CommentSummary> comments
) {
	public static CommentGetCursorResponse from(
		final CommentsGetServiceResponse response
	) {
		return new CommentGetCursorResponse(
			response.commentSummaries().nextCursorId(),
			response.commentSummaries().summaryCount(),
			response.totalCommentCount(),
			response.commentSummaries().summaries()
		);
	}
}

