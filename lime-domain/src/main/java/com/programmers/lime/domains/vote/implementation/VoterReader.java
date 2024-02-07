package com.programmers.lime.domains.vote.implementation;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.domains.vote.domain.Vote;
import com.programmers.lime.domains.vote.domain.Voter;
import com.programmers.lime.domains.vote.repository.VoterRepository;
import com.programmers.lime.error.EntityNotFoundException;
import com.programmers.lime.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VoterReader {

	private final VoterRepository voterRepository;

	@Transactional(readOnly = true)
	public Long readItemId(
		final Vote vote,
		final Long memberId
	) {
		return voterRepository.findByVoteAndMemberId(vote, memberId)
			.map(Voter::getItemId).orElse(null);
	}

	@Transactional(readOnly = true)
	public Voter read(
		final Vote vote,
		final Long memberId,
		final Long itemId
	) {
		return voterRepository.findByVoteAndMemberId(vote, memberId)
			.orElseGet(() -> new Voter(vote, memberId, itemId));
	}

	@Transactional(readOnly = true)
	public Voter read(
		final Vote vote,
		final Long memberId
	) {
		return voterRepository.findByVoteAndMemberId(vote, memberId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.VOTER_NOT_FOUND));
	}
}
