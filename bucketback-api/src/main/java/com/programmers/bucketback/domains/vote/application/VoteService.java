package com.programmers.bucketback.domains.vote.application;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.common.cursor.CursorPageParameters;
import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.implementation.ItemReader;
import com.programmers.bucketback.domains.vote.application.dto.request.VoteCreateServiceRequest;
import com.programmers.bucketback.domains.vote.application.dto.response.VoteGetServiceResponse;
import com.programmers.bucketback.domains.vote.domain.Vote;
import com.programmers.bucketback.domains.vote.implementation.VoteAppender;
import com.programmers.bucketback.domains.vote.implementation.VoteManager;
import com.programmers.bucketback.domains.vote.implementation.VoteReader;
import com.programmers.bucketback.domains.vote.implementation.VoteRemover;
import com.programmers.bucketback.domains.vote.model.VoteDetail;
import com.programmers.bucketback.domains.vote.model.VoteSortCondition;
import com.programmers.bucketback.domains.vote.model.VoteStatusCondition;
import com.programmers.bucketback.domains.vote.model.VoteSummary;
import com.programmers.bucketback.error.BusinessException;
import com.programmers.bucketback.error.ErrorCode;
import com.programmers.bucketback.global.util.MemberUtils;
import com.programmers.bucketback.redis.vote.VoteRedis;
import com.programmers.bucketback.redis.vote.VoteRedisManager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteService {

	private final VoteAppender voteAppender;
	private final VoteReader voteReader;
	private final VoteManager voteManager;
	private final VoteRemover voteRemover;
	private final MemberUtils memberUtils;
	private final ItemReader itemReader;
	private final VoteRedisManager voteRedisManager;

	public Long createVote(final VoteCreateServiceRequest request) {
		final Long memberId = memberUtils.getCurrentMemberId();
		final Vote vote = voteAppender.append(memberId, request.toImplRequest());

		final VoteRedis voteRedis = getVoteRedis(vote);
		voteRedisManager.addRanking(voteRedis);

		return vote.getId();
	}

	public void participateVote(
		final Long voteId,
		final Long itemId
	) {
		final Long memberId = memberUtils.getCurrentMemberId();
		final Vote vote = voteReader.read(voteId);

		if (!vote.isVoting()) {
			throw new BusinessException(ErrorCode.VOTE_CANNOT_PARTICIPATE);
		}

		if (!vote.containsItem(itemId)) {
			throw new BusinessException(ErrorCode.VOTE_NOT_CONTAIN_ITEM);
		}

		voteManager.participate(vote, memberId, itemId);

		final VoteRedis voteRedis = getVoteRedis(vote);
		voteRedisManager.increasePopularity(voteRedis);
	}

	public void cancelVote(final Long voteId) {
		final Long memberId = memberUtils.getCurrentMemberId();
		final Vote vote = voteReader.read(voteId);

		voteManager.cancel(vote, memberId);
	}

	public void deleteVote(final Long voteId) {
		final Long memberId = memberUtils.getCurrentMemberId();
		final Vote vote = voteReader.read(voteId);

		if (!vote.isOwner(memberId)) {
			throw new BusinessException(ErrorCode.VOTE_NOT_OWNER);
		}

		voteRemover.remove(vote);
	}

	public VoteGetServiceResponse getVote(final Long voteId) {
		final Long memberId = memberUtils.getCurrentMemberId();
		final VoteDetail detail = voteReader.read(voteId, memberId);

		return VoteGetServiceResponse.from(detail);
	}

	public CursorSummary<VoteSummary> getVotesByCursor(
		final Hobby hobby,
		final VoteStatusCondition statusCondition,
		final VoteSortCondition sortCondition,
		final CursorPageParameters parameters
	) {
		final Long memberId = memberUtils.getCurrentMemberId();

		if (memberId == null && statusCondition.isRequiredLogin()) {
			throw new BusinessException(ErrorCode.UNAUTHORIZED);
		}

		if (sortCondition.isImpossibleSort(statusCondition)) {
			throw new BusinessException(ErrorCode.VOTE_CANNOT_SORT);
		}

		return voteReader.readByCursor(
			hobby,
			statusCondition,
			sortCondition,
			null,
			parameters,
			memberId
		);
	}

	public CursorSummary<VoteSummary> getVotesByKeyword(
		final String keyword,
		final CursorPageParameters parameters
	) {
		if (keyword.isBlank()) {
			return new CursorSummary<>(null, 0, Collections.emptyList());
		}

		return voteReader.readByCursor(
			null,
			VoteStatusCondition.COMPLETED,
			VoteSortCondition.RECENT,
			keyword,
			parameters,
			null
		);
	}

	public List<VoteRedis> rankVote() {
		return voteRedisManager.getRanking();
	}

	private VoteRedis getVoteRedis(final Vote vote) {
		final Item item1 = itemReader.read(vote.getItem1Id());
		final Item item2 = itemReader.read(vote.getItem2Id());

		return VoteRedis.builder()
			.id(vote.getId())
			.content(vote.getContent())
			.startTime(String.valueOf(vote.getStartTime()))
			.item1Id(vote.getItem1Id())
			.item1Name(item1.getName())
			.item2Id(vote.getItem2Id())
			.item2Name(item2.getName())
			.build();
	}
}
