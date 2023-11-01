package com.programmers.bucketback.domains.vote.application;

import static com.programmers.bucketback.global.error.exception.ErrorCode.*;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.vote.domain.Vote;
import com.programmers.bucketback.domains.vote.domain.Voter;
import com.programmers.bucketback.domains.vote.repository.VoterRepository;
import com.programmers.bucketback.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VoterReader {

	private final VoterRepository voterRepository;

	public Voter read(
		final Vote vote,
		final Long memberId
	) {
		return voterRepository.findByVoteAndMemberId(vote, memberId)
			.orElseThrow(() -> new BusinessException(VOTE_NOT_VOTER));
	}
}
