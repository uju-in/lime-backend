package com.programmers.bucketback.domains.vote.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.vote.domain.Vote;
import com.programmers.bucketback.domains.vote.repository.VoteReposiory;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VoteRemover {

	private final VoteReposiory voteReposiory;

	@Transactional
	public void remove(final Vote vote) {
		voteReposiory.delete(vote);
	}
}
