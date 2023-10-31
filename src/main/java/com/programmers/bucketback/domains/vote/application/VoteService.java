package com.programmers.bucketback.domains.vote.application;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.vote.application.dto.request.CreateVoteServiceRequest;
import com.programmers.bucketback.domains.vote.domain.Vote;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteService {

	private final VoteAppender voteAppender;

	public Long createVote(final CreateVoteServiceRequest request) {
		final Long memberId = MemberUtils.getCurrentMemberId();

		final Vote vote = voteAppender.append(memberId, request);

		return vote.getId();
	}
}
