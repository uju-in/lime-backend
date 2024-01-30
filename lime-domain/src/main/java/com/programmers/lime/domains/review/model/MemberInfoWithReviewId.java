package com.programmers.lime.domains.review.model;

import com.programmers.lime.domains.member.model.MemberInfo;

public record MemberInfoWithReviewId(
	Long reviewId,
	MemberInfo memberInfo
) {
}
