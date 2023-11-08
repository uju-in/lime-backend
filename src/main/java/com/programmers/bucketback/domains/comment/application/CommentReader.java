package com.programmers.bucketback.domains.comment.application;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.comment.domain.Comment;
import com.programmers.bucketback.domains.comment.repository.CommentRepository;
import com.programmers.bucketback.domains.comment.repository.CommentSummary;
import com.programmers.bucketback.domains.common.vo.CursorPageParameters;
import com.programmers.bucketback.global.error.exception.EntityNotFoundException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentReader {

	private final CommentRepository commentRepository;

	public Comment read(final Long commentId) {
		return commentRepository.findById(commentId)
			.orElseThrow(
				() -> new EntityNotFoundException(ErrorCode.COMMENT_NOT_FOUND)
			);
	}

	public CommentCursorSummary readByCursor(
		final Long feedId,
		final Long memberId,
		final CursorPageParameters parameters
	) {
		int pageSize = parameters.size() == null ? 20 : parameters.size();

		List<CommentSummary> summaries = commentRepository.findByCursor(
			feedId,
			memberId,
			parameters.cursorId(),
			pageSize
		);

		String nextCursorId = summaries.size() == 0 ? null : summaries.get(summaries.size() - 1).cursorId();
		int summaryCount = summaries.size();

		return new CommentCursorSummary(nextCursorId, summaryCount, summaries);
	}
}
