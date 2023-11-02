package com.programmers.bucketback.domains.vote.application;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.programmers.bucketback.domains.vote.domain.Vote;

import lombok.Builder;

@Builder
public record VoteInfo(
	Long id,
	String content,
	LocalDateTime createAt,
	boolean isVoting,
	int participants,

	@JsonInclude(JsonInclude.Include.NON_NULL)
	Integer option1Votes,

	@JsonInclude(JsonInclude.Include.NON_NULL)
	Integer option2Votes
) {
	public static VoteInfo of(
		final Vote vote,
		final int option1Votes,
		final int option2Votes
	) {
		return VoteInfo.builder()
			.id(vote.getId())
			.content(vote.getContent())
			.createAt(vote.getCreatedAt())
			.isVoting(isVoting(vote.getEndTime()))
			.participants(option1Votes + option2Votes)
			.option1Votes(option1Votes)
			.option2Votes(option2Votes)
			.build();
	}

	private static boolean isVoting(final LocalDateTime endTime) {
		return LocalDateTime.now()
			.isAfter(endTime);
	}
}
