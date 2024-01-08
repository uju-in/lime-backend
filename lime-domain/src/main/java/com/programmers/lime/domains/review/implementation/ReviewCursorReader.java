package com.programmers.lime.domains.review.implementation;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.common.cursor.CursorUtils;
import com.programmers.lime.domains.review.model.ReviewCursorSummary;
import com.programmers.lime.domains.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewCursorReader {

	private final static int DEFAULT_PAGE_SIZE = 20;

	private final ReviewRepository reviewRepository;

	public CursorSummary<ReviewCursorSummary> readByCursor(
		final Long itemId,
		final Long memberId,
		final CursorPageParameters parameters
	) {
		int pageSize = getPageSize(parameters);

		List<ReviewCursorSummary> reviewCursorSummaries = reviewRepository.findAllByCursor(
			itemId,
			memberId,
			parameters.cursorId(),
			pageSize
		);

		return CursorUtils.getCursorSummaries(reviewCursorSummaries);
	}

	private int getPageSize(final CursorPageParameters parameters) {
		Integer parametersSize = parameters.size();

		if (parametersSize == null) {
			return DEFAULT_PAGE_SIZE;
		}

		return parametersSize;
	}
}
