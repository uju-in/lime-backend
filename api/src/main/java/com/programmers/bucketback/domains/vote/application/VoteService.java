package com.programmers.bucketback.domains.vote.application;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.Hobby;
import com.programmers.bucketback.common.cursor.CursorPageParameters;
import com.programmers.bucketback.domains.vote.domain.Vote;
import com.programmers.bucketback.domains.vote.implementation.VoteAppender;
import com.programmers.bucketback.domains.vote.implementation.VoteManager;
import com.programmers.bucketback.domains.vote.implementation.VoteReader;
import com.programmers.bucketback.domains.vote.implementation.VoteRemover;
import com.programmers.bucketback.domains.vote.model.request.VoteCreateServiceRequest;
import com.programmers.bucketback.domains.vote.model.request.VoteSortCondition;
import com.programmers.bucketback.domains.vote.model.request.VoteStatusCondition;
import com.programmers.bucketback.domains.vote.model.response.VoteGetServiceResponse;
import com.programmers.bucketback.domains.vote.model.response.VotesGetServiceResponse;
import com.programmers.bucketback.error.exception.BusinessException;
import com.programmers.bucketback.error.exception.ErrorCode;
import com.programmers.bucketback.global.util.MemberUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteService {

	private final VoteAppender voteAppender;
	private final VoteReader voteReader;
	private final VoteManager voteManager;
	private final VoteRemover voteRemover;

	public Long createVote(final VoteCreateServiceRequest request) {
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

		if (!vote.isVoting()) {
			throw new BusinessException(ErrorCode.VOTE_CANNOT_PARTICIPATE);
		}

		if (!vote.containsItem(itemId)) {
			throw new BusinessException(ErrorCode.VOTE_NOT_CONTAIN_ITEM);
		}

		voteManager.participate(vote, memberId, itemId);
	}

	public void cancelVote(final Long voteId) {
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

	public VoteGetServiceResponse getVote(final Long voteId) {
		Long memberId = MemberUtils.getCurrentMemberId();

		return voteReader.read(voteId, memberId);
	}

	public VotesGetServiceResponse getVotesByCursor(
		final Hobby hobby,
		final VoteStatusCondition statusCondition,
		final VoteSortCondition sortCondition,
		final CursorPageParameters parameters
	) {
		Long memberId = MemberUtils.getCurrentMemberId();

		return voteReader.readByCursor(hobby, statusCondition, sortCondition, parameters, memberId);
	}
}
