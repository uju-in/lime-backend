package com.programmers.lime.domains.review.application;

import org.springframework.stereotype.Service;

import com.programmers.lime.domains.review.implementation.ReviewLikeAppender;
import com.programmers.lime.domains.review.implementation.ReviewLikeRemover;
import com.programmers.lime.domains.review.implementation.ReviewLikeValidator;
import com.programmers.lime.global.util.MemberUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewLikeService {

	private final ReviewLikeAppender reviewLikeAppender;
	private final ReviewLikeRemover reviewLikeRemover;
	private final ReviewLikeValidator reviewLikeValidator;
	private final MemberUtils memberUtils;

	public void like(
		final Long reviewId
	) {
		Long memberId = memberUtils.getCurrentMemberId();
		reviewLikeValidator.validateReviewLike(memberId, reviewId);
		reviewLikeAppender.append(memberId, reviewId);
	}

	public void unlike(
		final Long reviewId
	) {
		Long memberId = memberUtils.getCurrentMemberId();
		reviewLikeValidator.validateReviewUnlike(memberId, reviewId);
		reviewLikeRemover.deleteByMemberIdAndReviewId(memberId, reviewId);
	}
}
