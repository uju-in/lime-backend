package com.programmers.lime.domains.vote.implementation;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.domains.vote.domain.Vote;
import com.programmers.lime.domains.vote.repository.VoteRepository;
import com.programmers.lime.domains.vote.repository.VoterRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VoteRemover {

	private final VoteRepository voteRepository;
	private final VoterRepository voterRepository;

	@Transactional
	public void remove(final Vote vote) {
		voteRepository.delete(vote);
		voterRepository.deleteByVoteId(vote.getId());
	}
}
