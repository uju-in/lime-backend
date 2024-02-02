package com.programmers.lime.domains.review.model;

import com.programmers.lime.domains.member.model.MemberInfo;

import lombok.Builder;

@Builder
public record ReviewLoginMemberStatus(
	boolean isReviewed,
	boolean isLiked
) {

	public static ReviewLoginMemberStatus of(
		final MemberInfo memberInfo,
		final ReviewInfo reviewInfo,
		final Long loginMemberId
	) {
		return ReviewLoginMemberStatus.builder()
			.isReviewed(memberInfo.memberId().equals(loginMemberId))
			.isLiked(reviewInfo.isLiked())
			.build();
	}
}
