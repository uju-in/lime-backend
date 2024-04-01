package com.programmers.lime.domains.vote.implementation;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.domains.vote.domain.Voter;
import com.programmers.lime.domains.vote.repository.VoterRepository;
import com.programmers.lime.error.EntityNotFoundException;
import com.programmers.lime.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VoterReader {

	private final VoterRepository voterRepository;

	@Transactional(readOnly = true) // 삭제하기
	public Long readItemId(
		final Long voteId,
		final Long memberId
	) {
		return voterRepository.findByVoteIdAndMemberId(voteId, memberId)
			.map(Voter::getItemId).orElse(null);
	}

	@Transactional(readOnly = true)
	public Optional<Voter> find(
		final Long voteId,
		final Long memberId
	) {
		return voterRepository.findByVoteIdAndMemberId(voteId, memberId);
	}

	@Transactional(readOnly = true)
	public Voter read(
		final Long voteId,
		final Long memberId
	) {
		return voterRepository.findByVoteIdAndMemberId(voteId, memberId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.VOTER_NOT_FOUND));
	}

	@Transactional(readOnly = true)
	public int count(final Long voteId) {
		return voterRepository.countByVoteId(voteId);
	}

	@Transactional(readOnly = true)
	public int count(
		final Long voteId,
		final Long itemId
	) {
		return voterRepository.countByVoteIdAndItemId(voteId, itemId);
	}
}
