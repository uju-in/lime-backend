package com.programmers.lime.domains.vote.model;

import java.time.LocalDateTime;

import com.programmers.lime.domains.vote.domain.Vote;

import lombok.Builder;

@Builder
public record VoteDetailInfo(
	Long id,
	String content,
	LocalDateTime startTime,
	LocalDateTime endTime,
	int maxParticipants,
	boolean isVoting,
	int participants,
	int item1Votes,
	int item2Votes
) {
	public static VoteDetailInfo of(
		final Vote vote,
		final int item1Votes,
		final int item2Votes
	) {
		return VoteDetailInfo.builder()
			.id(vote.getId())
			.content(vote.getContent())
			.startTime(vote.getStartTime())
			.endTime(vote.getEndTime())
			.maxParticipants(vote.getMaximumParticipants())
			.isVoting(vote.isVoting())
			.participants(item1Votes + item2Votes)
			.item1Votes(item1Votes)
			.item2Votes(item2Votes)
			.build();
	}
}
