package com.programmers.bucketback.domains.vote.implementation;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.vote.domain.Vote;
import com.programmers.bucketback.domains.vote.domain.Voter;
import com.programmers.bucketback.domains.vote.repository.VoterRepository;

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
		final Voter voter = voterReader.read(vote, memberId, itemId);

		voter.participate(itemId);

		if (vote.reachMaximumParticipants()) {
			vote.close(LocalDateTime.now());
		}
	}

	@Transactional
	public void cancel(
		final Vote vote,
		final Long memberId
	) {
		voterRepository.deleteByVoteAndMemberId(vote, memberId);
	}
}
