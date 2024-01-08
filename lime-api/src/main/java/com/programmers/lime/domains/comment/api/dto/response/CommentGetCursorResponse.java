package com.programmers.lime.domains.comment.api.dto.response;

import java.util.List;

import com.programmers.lime.domains.comment.application.dto.response.CommentGetCursorServiceResponse;
import com.programmers.lime.domains.comment.repository.CommentSummary;

public record CommentGetCursorResponse(
	String nextCursorId,
	int totalCount,
	int totalCommentCount,
	List<CommentSummary> comments
) {
	public static CommentGetCursorResponse from(
		final CommentGetCursorServiceResponse response
	) {
		return new CommentGetCursorResponse(
			response.commentSummary().nextCursorId(),
			response.commentSummary().summaryCount(),
			response.totalCommentCount(),
			response.commentSummary().summaries()
		);
	}
}

