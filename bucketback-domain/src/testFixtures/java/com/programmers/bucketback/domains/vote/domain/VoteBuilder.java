package com.programmers.bucketback.domains.vote.domain;

import org.springframework.test.util.ReflectionTestUtils;

import com.programmers.bucketback.common.model.Hobby;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VoteBuilder {
	public static Vote build() {
		return build(1L);
	}

	public static Vote build(final Long memberId) {
		Vote vote = Vote.builder()
			.memberId(memberId)
			.item1Id(1L)
			.item2Id(2L)
			.hobby(Hobby.BASKETBALL)
			.content("농구공 사려는데 뭐가 더 좋음?")
			.build();

		setVoteId(vote);

		return vote;
	}

	private static void setVoteId(final Vote vote) {
		ReflectionTestUtils.setField(
			vote,
			"id",
			1L
		);
	}
}
