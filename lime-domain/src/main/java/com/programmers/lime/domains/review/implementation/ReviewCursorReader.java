package com.programmers.lime.domains.review.implementation;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.common.cursor.CursorUtils;
import com.programmers.lime.domains.member.model.MemberInfo;
import com.programmers.lime.domains.review.model.MemberInfoWithReviewId;
import com.programmers.lime.domains.review.model.ReviewCursorIdInfo;
import com.programmers.lime.domains.review.model.ReviewCursorSummary;
import com.programmers.lime.domains.review.model.ReviewSortCondition;
import com.programmers.lime.domains.review.model.ReviewSummary;
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
		final CursorPageParameters parameters,
		final ReviewSortCondition sortCondition
	) {
		int pageSize = getPageSize(parameters);

		List<ReviewCursorIdInfo> reviewCursorIdInfos = reviewRepository.findAllByCursor(
			itemId,
			parameters.cursorId(),
			pageSize,
			sortCondition
		);

		List<ReviewCursorSummary> reviewCursorSummaries = getReviewCursorSummaries(reviewCursorIdInfos, memberId);

		return CursorUtils.getCursorSummaries(reviewCursorSummaries);
	}

	private List<ReviewCursorSummary> getReviewCursorSummaries(
		final List<ReviewCursorIdInfo> reviewCursorIdInfos,
		final Long memberId
	) {

		// 리뷰 아이디 리스트를 가져옴
		List<Long> reviewIds = reviewCursorIdInfos.stream()
			.map(ReviewCursorIdInfo::reviewId)
			.toList();

		// 리뷰 아이디를 이용하여 리뷰 정보를 가져옴
		Map<Long, ReviewSummary> reviewSummaryMap = reviewRepository.getReviewSummaries(reviewIds)
			.stream()
			.collect(Collectors.toMap(ReviewSummary::reviewId, Function.identity()));

		// 리뷰 아이디를 이용하여 멤버 정보를 가져옴
		Map<Long, MemberInfoWithReviewId> memberInfoMap = reviewRepository.getMemberInfos(reviewIds)
			.stream()
			.collect(Collectors.toMap(MemberInfoWithReviewId::reviewId, Function.identity()));


		return reviewCursorIdInfos.stream()
			.map(reviewCursorIdInfo -> {
				ReviewSummary reviewSummary = reviewSummaryMap.get(reviewCursorIdInfo.reviewId());
				MemberInfoWithReviewId memberInfoWithReviewId = memberInfoMap.get(reviewCursorIdInfo.reviewId());
				MemberInfo memberInfo = memberInfoWithReviewId.memberInfo();

				return ReviewCursorSummary.builder()
					.cursorId(reviewCursorIdInfo.cursorId())
					.reviewSummary(reviewSummary)
					.memberInfo(memberInfoWithReviewId.memberInfo())
					.isReviewed(memberInfo.memberId().equals(memberId))
					.build();
			})
			.toList();
	}

	private int getPageSize(final CursorPageParameters parameters) {
		Integer parametersSize = parameters.size();

		if (parametersSize == null) {
			return DEFAULT_PAGE_SIZE;
		}

		return parametersSize;
	}
}
