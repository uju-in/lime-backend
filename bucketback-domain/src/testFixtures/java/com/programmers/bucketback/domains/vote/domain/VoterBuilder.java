package com.programmers.bucketback.domains.vote.domain;

import java.util.List;
import java.util.stream.LongStream;

import org.springframework.test.util.ReflectionTestUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VoterBuilder {
	public static List<Voter> buildMany(
		final int size,
		final Vote vote,
		final Long itemId
	) {
		return LongStream.range(0, size)
			.mapToObj(i -> VoterBuilder.build(i + 1, vote, i + 1, itemId))
			.toList();
	}

	public static Voter build(
		final Long id,
		final Vote vote,
		final Long memberId,
		final Long itemId
	) {
		Voter voter = new Voter(vote, memberId, itemId);

		setVoterId(id, voter);

		return voter;
	}

	private static void setVoterId(
		final Long id,
		final Voter voter
	) {
		ReflectionTestUtils.setField(
			voter,
			"id",
			id
		);
	}
}
