package com.programmers.bucketback.domains.comment.repository;

import java.util.List;

public interface CommentRepositoryForCursor {
	List<CommentSummary> findByCursor(
		Long feedId,
		Long memberId,
		String cursorId,
		int pageSize
	);
}
