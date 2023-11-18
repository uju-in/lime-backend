package com.programmers.bucketback.domains.vote.implementation;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.vote.domain.Vote;
import com.programmers.bucketback.domains.vote.domain.Voter;
import com.programmers.bucketback.domains.vote.repository.VoterRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VoterAppender {

	private final VoterRepository voterRepository;

	public Voter append(final Vote vote, final Long memberId, final Long itemId) {
		Voter voter = new Voter(vote, memberId, itemId);

		return voterRepository.save(voter);
	}
}
