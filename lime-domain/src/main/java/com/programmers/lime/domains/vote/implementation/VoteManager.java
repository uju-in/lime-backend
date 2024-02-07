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
		final Voter voter = new Voter(vote, memberId, itemId);

		voter.participate(itemId);

		if (vote.reachMaximumParticipants()) {
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
		final Vote vote,
		final Long memberId
	) {
		final Voter voter = voterReader.read(vote, memberId);
		voterRepository.delete(voter);
	}
}
