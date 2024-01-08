package com.programmers.lime.domains.comment.repository;

import java.util.List;

public interface CommentRepositoryForCursor {
	List<CommentSummary> findByCursor(
		Long feedId,
		Long memberId,
		String cursorId,
		int pageSize
	);
}
