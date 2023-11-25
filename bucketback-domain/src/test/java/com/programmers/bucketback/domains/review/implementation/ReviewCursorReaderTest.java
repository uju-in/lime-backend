package com.programmers.bucketback.domains.review.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.bucketback.common.cursor.CursorPageParameters;
import com.programmers.bucketback.common.cursor.CursorPageParametersBuilder;
import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.common.cursor.CursorUtils;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.domain.ItemBuilder;
import com.programmers.bucketback.domains.review.ReviewCursorSummaryBuilder;
import com.programmers.bucketback.domains.review.model.ReviewCursorSummary;
import com.programmers.bucketback.domains.review.repository.ReviewRepository;

@ExtendWith(MockitoExtension.class)
class ReviewCursorReaderTest {

	@InjectMocks
	private ReviewCursorReader reviewCursorReader;

	@Mock
	private ReviewRepository reviewRepository;

	@Nested
	@DisplayName("리뷰 목록을 조회 테스트")
	class ReviewReadTest {

		@Test
		@DisplayName("커서를 이용해 리뷰 목록을 조회한다.")
		void readByCursor() {
			// given
			Item item = ItemBuilder.build();
			Long itemId = item.getId();
			Long memberId = 1L;

			CursorPageParameters cursorPageParameters = CursorPageParametersBuilder.build();
			List<ReviewCursorSummary> reviewCursorSummaries = ReviewCursorSummaryBuilder.buildMany();

			CursorSummary<ReviewCursorSummary> expectedReviewCursorSummary = CursorUtils
				.getCursorSummaries(reviewCursorSummaries);

			given(reviewRepository.findAllByCursor(
					itemId,
					memberId,
					cursorPageParameters.cursorId(),
					cursorPageParameters.size()
				)
			).willReturn(reviewCursorSummaries);

			// when
			CursorSummary<ReviewCursorSummary> actualReviewCursorSummary = reviewCursorReader.readByCursor(
				itemId,
				memberId,
				cursorPageParameters
			);

			// then
			assertThat(actualReviewCursorSummary)
				.isNotNull()
				.isEqualTo(expectedReviewCursorSummary);
		}

		@Test
		@DisplayName("CursorId와 size가 null일 때 기본값으로 리뷰 목록을 조회한다.")
		void readByCursorWithNullCursorIdAndSize() {
			// given
			Item item = ItemBuilder.build();
			Long itemId = item.getId();
			Long memberId = 1L;

			CursorPageParameters cursorPageParameters = CursorPageParametersBuilder.buildWithNull();
			List<ReviewCursorSummary> reviewCursorSummaries = ReviewCursorSummaryBuilder.buildMany();

			CursorSummary<ReviewCursorSummary> expectedReviewCursorSummary = CursorUtils
				.getCursorSummaries(reviewCursorSummaries);

			given(reviewRepository.findAllByCursor(
					itemId,
					memberId,
					null,
					20
				)
			).willReturn(reviewCursorSummaries);

			// when
			CursorSummary<ReviewCursorSummary> actualReviewCursorSummary = reviewCursorReader.readByCursor(
				itemId,
				memberId,
				cursorPageParameters
			);

			// then
			assertThat(actualReviewCursorSummary)
				.isNotNull()
				.isEqualTo(expectedReviewCursorSummary);
		}
	}

}
