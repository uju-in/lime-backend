package com.programmers.lime.domains.review.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorPageParametersBuilder;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.common.cursor.CursorUtils;
import com.programmers.lime.domains.item.domain.Item;
import com.programmers.lime.domains.item.domain.ItemBuilder;
import com.programmers.lime.domains.member.domain.MemberInfoWithReviewIdBuilder;
import com.programmers.lime.domains.review.ReviewCursorIdInfoBuilder;
import com.programmers.lime.domains.review.ReviewCursorSummaryBuilder;
import com.programmers.lime.domains.review.ReviewSummaryBuilder;
import com.programmers.lime.domains.review.model.MemberInfoWithReviewId;
import com.programmers.lime.domains.review.model.ReviewCursorIdInfo;
import com.programmers.lime.domains.review.model.ReviewCursorSummary;
import com.programmers.lime.domains.review.model.ReviewSummary;
import com.programmers.lime.domains.review.repository.ReviewRepository;

@ExtendWith(MockitoExtension.class)
class ReviewCursorReaderTest {

	private static final int DEFAULT_PAGE_SIZE = 20;

	@InjectMocks
	private ReviewCursorReader reviewCursorReader;

	@Mock
	private ReviewRepository reviewRepository;

	static Stream<Arguments> provideParameters() {
		return Stream.of(
			Arguments.of(CursorPageParametersBuilder.build()),
			Arguments.of(CursorPageParametersBuilder.buildWithNull())
		);
	}

	@ParameterizedTest
	@MethodSource("provideParameters")
	@DisplayName("커서를 이용해 리뷰 목록을 조회한다.")
	void readByCursor(final CursorPageParameters parameters) {
		// given
		Item item = ItemBuilder.build();
		Long itemId = item.getId();
		Long memberId = 0L;

		List<ReviewCursorIdInfo> reviewCursorIdInfos = ReviewCursorIdInfoBuilder.buildMany();
		List<Long> reviewIds = reviewCursorIdInfos.stream().map(ReviewCursorIdInfo::reviewId).toList();

		List<ReviewCursorSummary> reviewCursorSummaries = ReviewCursorSummaryBuilder.buildMany(reviewIds);

		CursorSummary<ReviewCursorSummary> expectedReviewCursorSummary = CursorUtils
			.getCursorSummaries(reviewCursorSummaries);

		List<MemberInfoWithReviewId> memberInfoWithReviewIds = MemberInfoWithReviewIdBuilder.buildMany(reviewIds);
		List<ReviewSummary> reviewSummaries = ReviewSummaryBuilder.buildMany(reviewIds);

		int pageSize = parameters.size() == null ? DEFAULT_PAGE_SIZE : parameters.size();
		given(reviewRepository.findAllByCursor(
				itemId,
				parameters.cursorId(),
				pageSize
			)
		).willReturn(reviewCursorIdInfos);

		given(reviewRepository.getMemberInfos(reviewIds))
			.willReturn(memberInfoWithReviewIds);

		given(reviewRepository.getReviewSummaries(reviewIds))
			.willReturn(reviewSummaries);

		// when
		CursorSummary<ReviewCursorSummary> actualReviewCursorSummary = reviewCursorReader.readByCursor(
			itemId,
			memberId,
			parameters
		);

		// then
		assertThat(actualReviewCursorSummary)
			.isNotNull()
			.isEqualTo(expectedReviewCursorSummary);
	}
}
