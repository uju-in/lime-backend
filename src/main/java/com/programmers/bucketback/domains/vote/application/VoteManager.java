package com.programmers.bucketback.domains.vote.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.vote.domain.Vote;
import com.programmers.bucketback.domains.vote.domain.Voter;
import com.programmers.bucketback.domains.vote.repository.VoterRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VoteManager {

	private final VoterRepository voterRepository;

	@Transactional
	public void vote(
		final Vote vote,
		final Long memberId,
		final Long itemId
	) {
		final Voter voter = voterRepository.findByVoteAndMemberId(vote, memberId)
			.orElseGet(() -> new Voter(vote, memberId, itemId));

		voterRepository.save(voter);

		voter.changeItem(itemId);
		vote.addVoter(voter);
	}
}
