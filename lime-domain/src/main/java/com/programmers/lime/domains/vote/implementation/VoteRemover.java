package com.programmers.lime.domains.vote.implementation;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.domains.vote.domain.Vote;
import com.programmers.lime.domains.vote.repository.VoteRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VoteRemover {

	private final VoteRepository voteRepository;

	@Transactional
	public void remove(final Vote vote) {
		voteRepository.delete(vote);
	}
}
