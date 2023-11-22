package com.programmers.bucketback.domains.vote.model;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record VoteInfo(
	Long id,
	String content,
	LocalDateTime startTime,
	boolean isVoting,
	int participants
) {
	public VoteInfo(
		final Long id,
		final String content,
		final LocalDateTime startTime,
		final LocalDateTime endTime,
		final int participants
	) {
		this(id, content, startTime, isVoting(endTime), participants);
	}

	private static boolean isVoting(final LocalDateTime endTime) {
		return LocalDateTime.now()
			.isBefore(endTime);
	}
}
