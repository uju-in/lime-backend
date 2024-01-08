package com.programmers.lime.domains.vote.implementation;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.vote.domain.Vote;
import com.programmers.lime.domains.vote.repository.VoterRepository;

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
