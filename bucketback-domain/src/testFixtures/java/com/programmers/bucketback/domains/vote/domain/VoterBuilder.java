package com.programmers.bucketback.domains.vote.domain;

import java.util.List;
import java.util.stream.LongStream;

import org.springframework.test.util.ReflectionTestUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VoterBuilder {
	public static List<Voter> buildMany(
		final long size,
		final Vote vote,
		final Long itemId
	) {
		return LongStream.range(1, size + 1)
			.mapToObj(i -> {
				Voter voter = VoterBuilder.build(vote, i, itemId);
				setVoterId(voter, i);

				return voter;
			})
			.toList();
	}

	public static Voter build(
		final Vote vote,
		final Long memberId,
		final Long itemId
	) {
		final Voter voter = new Voter(vote, memberId, itemId);

		setVoterId(voter, 1L);

		return voter;
	}

	private static void setVoterId(
		final Voter voter,
		final Long id
	) {
		ReflectionTestUtils.setField(
			voter,
			"id",
			id
		);
	}
}
