package com.programmers.bucketback.domains.vote.application;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.common.vo.CursorPageParameters;
import com.programmers.bucketback.domains.vote.application.dto.request.CreateVoteServiceRequest;
import com.programmers.bucketback.domains.vote.application.dto.response.GetVoteServiceResponse;
import com.programmers.bucketback.domains.vote.application.dto.response.GetVotesServiceResponse;
import com.programmers.bucketback.domains.vote.domain.Vote;
import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteService {

	private final VoteAppender voteAppender;
	private final VoteReader voteReader;
	private final VoteManager voteManager;
	private final VoteRemover voteRemover;

	public Long createVote(final CreateVoteServiceRequest request) {
		final Long memberId = MemberUtils.getCurrentMemberId();

		final Vote vote = voteAppender.append(memberId, request);

		return vote.getId();
	}

	public void participateVote(
		final Long voteId,
		final Long itemId
	) {
		final Long memberId = MemberUtils.getCurrentMemberId();
		final Vote vote = voteReader.read(voteId);

		if (!vote.containsItem(itemId)) {
			throw new BusinessException(ErrorCode.VOTE_NOT_CONTAIN_ITEM);
		}

		voteManager.vote(vote, memberId, itemId);
	}

	public void cancelVote(final Long voteId) {
		if (!MemberUtils.isLoggedIn()) {
			throw new BusinessException(ErrorCode.UNAUTHORIZED);
		}

		final Long memberId = MemberUtils.getCurrentMemberId();
		final Vote vote = voteReader.read(voteId);

		voteManager.cancel(vote, memberId);
	}

	public void deleteVote(final Long voteId) {
		final Long memberId = MemberUtils.getCurrentMemberId();
		final Vote vote = voteReader.read(voteId);

		if (!vote.isOwner(memberId)) {
			throw new BusinessException(ErrorCode.VOTE_NOT_OWNER);
		}

		voteRemover.remove(vote);
	}

	public GetVoteServiceResponse getVote(final Long voteId) {
		Long memberId = null;
		if (MemberUtils.isLoggedIn()) {
			memberId = MemberUtils.getCurrentMemberId();
		}

		return voteReader.read(voteId, memberId);
	}

	public GetVotesServiceResponse getVotesByCursor(
		final Hobby hobby,
		final VoteStatusCondition statusCondition,
		final String sortCondition,
		final CursorPageParameters parameters
	) {
		Long memberId = null;
		if (MemberUtils.isLoggedIn()) {
			memberId = MemberUtils.getCurrentMemberId();
		}

		return voteReader.readByCursor(hobby, statusCondition, sortCondition, parameters, memberId);
	}
}
