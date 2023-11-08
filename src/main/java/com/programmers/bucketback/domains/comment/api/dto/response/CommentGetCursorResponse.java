package com.programmers.bucketback.domains.comment.api.dto.response;

import com.programmers.bucketback.domains.comment.application.CommentCursorSummary;

public record CommentGetCursorResponse(
	CommentCursorSummary commentCursorSummary
) {
}

