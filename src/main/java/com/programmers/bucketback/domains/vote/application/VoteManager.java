package com.programmers.bucketback.domains.vote.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.vote.domain.Vote;
import com.programmers.bucketback.domains.vote.domain.Voter;
import com.programmers.bucketback.domains.vote.repository.VoterRepository;
import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VoteManager {

	private final VoteReader voteReader;
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

	@Transactional
	public void cancel(final Long voteId) {
		if (!MemberUtils.isLoggedIn()) {
			throw new BusinessException(ErrorCode.MEMBER_ANONYMOUS); // 더 핏한 예러코드로 바꿀 예정
		}

		final Vote vote = voteReader.read(voteId);
		final Long memberId = MemberUtils.getCurrentMemberId();

		if (voterRepository.existsByVoteAndMemberId(vote, memberId)) {
			voterRepository.deleteByVoteAndMemberId(vote, memberId);
		}
	}
}
