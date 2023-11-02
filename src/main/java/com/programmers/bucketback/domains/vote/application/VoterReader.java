package com.programmers.bucketback.domains.vote.application;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.vote.domain.Vote;
import com.programmers.bucketback.domains.vote.domain.Voter;
import com.programmers.bucketback.domains.vote.repository.VoterRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VoterReader {

	private final VoterRepository voterRepository;

	public Optional<Voter> read(
		final Vote vote,
		final Long memberId
	) {
		return voterRepository.findByVoteAndMemberId(vote, memberId);
	}
}
