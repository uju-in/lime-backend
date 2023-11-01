package com.programmers.bucketback.domains.vote.application;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.vote.domain.Vote;
import com.programmers.bucketback.domains.vote.repository.VoterRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VoteCounter {

	private final VoterRepository voterRepository;

	public int count(
		final Vote vote,
		final Long itemId
	) {
		return voterRepository.countByVoteAndItemId(vote, itemId);
	}
}
