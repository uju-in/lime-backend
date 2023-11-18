package com.programmers.bucketback.domains.comment.api.dto.response;

import com.programmers.bucketback.domains.comment.model.CommentCursorSummary;

public record CommentGetCursorResponse(
	CommentCursorSummary commentCursorSummary
) {
}

