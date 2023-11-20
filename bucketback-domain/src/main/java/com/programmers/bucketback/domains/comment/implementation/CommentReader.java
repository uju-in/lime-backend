package com.programmers.bucketback.domains.comment.implementation;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.common.cursor.CursorPageParameters;
import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.common.cursor.CursorUtils;
import com.programmers.bucketback.domains.comment.domain.Comment;
import com.programmers.bucketback.domains.comment.repository.CommentRepository;
import com.programmers.bucketback.domains.comment.repository.CommentSummary;
import com.programmers.bucketback.error.EntityNotFoundException;
import com.programmers.bucketback.error.ErrorCode;

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

	private int getPageSize(final CursorPageParameters parameters) {
		int pageSize = parameters.size() == null ? DEFAULT_PAGING_SIZE : parameters.size();
		return pageSize;
	}
}
