package com.programmers.lime.domains.vote.implementation;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.domains.vote.domain.Vote;
import com.programmers.lime.domains.vote.domain.Voter;
import com.programmers.lime.domains.vote.repository.VoterRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VoteManager {

	private final VoterReader voterReader;
	private final VoterRepository voterRepository;

	@Transactional
	public void participate(
		final Vote vote,
		final Long memberId,
		final Long itemId
	) {
		final Voter voter = new Voter(vote.getId(), memberId, itemId);
		voterRepository.save(voter);

		final int participants = voterReader.count(vote.getId());
		if (vote.reachMaximumParticipants(participants)) {
			vote.close(LocalDateTime.now());
		}
	}

	@Transactional
	public void reParticipate(
		final Long itemId,
		final Voter voter
	) {
		voter.participate(itemId);
	}

	@Transactional
	public void cancel(
		final Long voteId,
		final Long memberId
	) {
		voterRepository.deleteByVoteIdAndMemberId(voteId, memberId);
	}
}
