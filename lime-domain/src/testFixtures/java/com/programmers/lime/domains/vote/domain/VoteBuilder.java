package com.programmers.lime.domains.vote.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.test.util.ReflectionTestUtils;

import com.programmers.lime.common.model.Hobby;

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
			.maximumParticipants(3)
			.build();

		setVoteId(vote);

		return vote;
	}

	public static Vote build(
		final Long voteId,
		final Long item1Id,
		final Long item2Id
	) {
		final Vote vote = Vote.builder()
			.memberId(1L)
			.item1Id(item1Id)
			.item2Id(item2Id)
			.hobby(Hobby.BASKETBALL)
			.content("농구공 사려는데 뭐가 더 좋음?")
			.maximumParticipants(3)
			.build();
		setVoteId(vote, voteId);

		return vote;
	}

	public static List<Vote> buildMany(final int size) {
		final List<Vote> votes = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			final Vote vote = build();
			setVoteId(vote, (long)(i + 1));
			votes.add(vote);
		}

		return votes;
	}

	private static void setVoteId(final Vote vote) {
		ReflectionTestUtils.setField(
			vote,
			"id",
			1L
		);
	}

	private static void setVoteId(
		final Vote vote,
		final Long id
	) {
		ReflectionTestUtils.setField(
			vote,
			"id",
			id
		);
	}
}
