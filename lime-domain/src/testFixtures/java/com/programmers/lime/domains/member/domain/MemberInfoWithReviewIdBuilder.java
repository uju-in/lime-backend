package com.programmers.lime.domains.member.domain;

import java.util.List;

import com.programmers.lime.domains.review.model.MemberInfoWithReviewId;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberInfoWithReviewIdBuilder {

	public static MemberInfoWithReviewId build(final Long id) {
		return new MemberInfoWithReviewId(
			id,
			MemberInfoBuilder.build(id)
		);
	}
	public static List<MemberInfoWithReviewId> buildMany(List<Long> ids) {
		return ids.stream()
			.map(MemberInfoWithReviewIdBuilder::build)
			.toList();
	}
}
