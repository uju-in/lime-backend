package com.programmers.lime.domains.comment.application.dto.response;

import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.domains.comment.repository.CommentSummary;

public record CommentGetCursorServiceResponse(
	CursorSummary<CommentSummary> commentSummary,
	int totalCommentCount
) {
}
