package com.programmers.bucketback.domains.vote.application.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.programmers.bucketback.domains.vote.domain.Vote;

import lombok.Builder;

@Builder
public record VoteInfo(
	Long id,
	String content,
	LocalDateTime startTime,
	boolean isVoting,
	int participants,

	@JsonInclude(JsonInclude.Include.NON_NULL)
	Integer item1Votes,

	@JsonInclude(JsonInclude.Include.NON_NULL)
	Integer item2Votes
) {
	public VoteInfo(
		final Long id,
		final String content,
		final LocalDateTime startTime,
		final LocalDateTime endTime,
		final int participants
	) {
		this(id, content, startTime, isVoting(endTime), participants, null, null);
	}

	public static VoteInfo of(
		final Vote vote,
		final int item1Votes,
		final int item2Votes
	) {
		return VoteInfo.builder()
			.id(vote.getId())
			.content(vote.getContent())
			.startTime(vote.getStartTime())
			.isVoting(isVoting(vote.getEndTime()))
			.participants(item1Votes + item2Votes)
			.item1Votes(item1Votes)
			.item2Votes(item2Votes)
			.build();
	}

	private static boolean isVoting(final LocalDateTime endTime) {
		return LocalDateTime.now()
			.isAfter(endTime);
	}
}
