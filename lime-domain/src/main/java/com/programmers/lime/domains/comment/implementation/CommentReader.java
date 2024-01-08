package com.programmers.lime.domains.comment.implementation;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.common.cursor.CursorUtils;
import com.programmers.lime.domains.comment.domain.Comment;
import com.programmers.lime.domains.comment.repository.CommentRepository;
import com.programmers.lime.domains.comment.repository.CommentSummary;
import com.programmers.lime.error.EntityNotFoundException;
import com.programmers.lime.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommentReader {

	private static final int DEFAULT_PAGING_SIZE = 20;
	private final CommentRepository commentRepository;

	@Transactional(readOnly = true)
	public Comment read(final Long commentId) {
		return commentRepository.findById(commentId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMMENT_NOT_FOUND));
	}

	public CursorSummary<CommentSummary> readByCursor(
		final Long feedId,
		final Long memberId,
		final CursorPageParameters parameters
	) {
		int pageSize = getPageSize(parameters);

		List<CommentSummary> summaries = commentRepository.findByCursor(
			feedId,
			memberId,
			parameters.cursorId(),
			pageSize
		);

		return CursorUtils.getCursorSummaries(summaries);
	}

	public int countComments(final Long feedId) {
		return commentRepository.countByFeedId(feedId);
	}

	private int getPageSize(final CursorPageParameters parameters) {
		int pageSize = parameters.size() == null ? DEFAULT_PAGING_SIZE : parameters.size();
		return pageSize;
	}

}
