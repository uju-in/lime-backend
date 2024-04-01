package com.programmers.lime.domains.vote.application;

import java.util.Collections;
import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.item.domain.Item;
import com.programmers.lime.domains.item.implementation.ItemReader;
import com.programmers.lime.domains.vote.application.dto.request.VoteCreateServiceRequest;
import com.programmers.lime.domains.vote.application.dto.response.VoteGetByKeywordServiceResponse;
import com.programmers.lime.domains.vote.application.dto.response.VoteGetServiceResponse;
import com.programmers.lime.domains.vote.domain.Vote;
import com.programmers.lime.domains.vote.implementation.VoteAppender;
import com.programmers.lime.domains.vote.implementation.VoteManager;
import com.programmers.lime.domains.vote.implementation.VoteReader;
import com.programmers.lime.domains.vote.implementation.VoteRemover;
import com.programmers.lime.domains.vote.implementation.VoterReader;
import com.programmers.lime.domains.vote.model.VoteDetail;
import com.programmers.lime.domains.vote.model.VoteSortCondition;
import com.programmers.lime.domains.vote.model.VoteStatusCondition;
import com.programmers.lime.domains.vote.model.VoteSummary;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.EntityNotFoundException;
import com.programmers.lime.error.ErrorCode;
import com.programmers.lime.global.event.ranking.vote.RankingAddEvent;
import com.programmers.lime.global.event.ranking.vote.RankingDecreasePopularityEvent;
import com.programmers.lime.global.event.ranking.vote.RankingDeleteEvent;
import com.programmers.lime.global.event.ranking.vote.RankingUpdateEvent;
import com.programmers.lime.global.util.MemberUtils;
import com.programmers.lime.redis.vote.VoteRankingInfo;
import com.programmers.lime.redis.vote.VoteRedisManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoteService {

	private final VoteAppender voteAppender;
	private final VoteReader voteReader;
	private final VoteManager voteManager;
	private final VoteRemover voteRemover;
	private final VoterReader voterReader;
	private final VoteLockManager voteLockManager;
	private final MemberUtils memberUtils;
	private final ItemReader itemReader;
	private final VoteRedisManager voteRedisManager;
	private final ApplicationEventPublisher eventPublisher;

	public Long createVote(final VoteCreateServiceRequest request) {
		final Long memberId = memberUtils.getCurrentMemberId();

		validateItemIds(request.item1Id(), request.item2Id());

		final Vote vote = voteAppender.append(memberId, request.toImplRequest());
		eventPublisher.publishEvent(new RankingAddEvent(String.valueOf(vote.getHobby()), getVoteRedis(vote)));

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

		participate(vote, memberId, itemId);
	}

	private void participate(
		final Vote vote,
		final Long memberId,
		final Long itemId
	) {
		voterReader.find(vote.getId(), memberId)
			.ifPresentOrElse(
				voter -> voteManager.reParticipate(itemId, voter),
				() -> {
					try {
						voteLockManager.participate(vote, memberId, itemId);
					} catch (InterruptedException e) {
						log.info("투표 참여 중 락 획득 실패, voteId={}, memberId={}", vote.getId(), memberId);
					}
					eventPublisher.publishEvent(new RankingUpdateEvent(String.valueOf(vote.getHobby()), vote.isVoting(), getVoteRedis(vote)));
				}
			);
	}

	public void cancelVote(final Long voteId) {
		final Long memberId = memberUtils.getCurrentMemberId();
		final Vote vote = voteReader.read(voteId);

		voteManager.cancel(voteId, memberId);
		eventPublisher.publishEvent(new RankingDecreasePopularityEvent(String.valueOf(vote.getHobby()), getVoteRedis(vote)));
	}

	public void deleteVote(final Long voteId) {
		final Long memberId = memberUtils.getCurrentMemberId();
		final Vote vote = voteReader.read(voteId);

		if (!vote.isOwner(memberId)) {
			throw new BusinessException(ErrorCode.VOTE_NOT_OWNER);
		}

		voteRemover.remove(vote);
		eventPublisher.publishEvent(new RankingDeleteEvent(String.valueOf(vote.getHobby()), getVoteRedis(vote)));
	}

	public VoteGetServiceResponse getVote(final Long voteId) {
		final Long memberId = memberUtils.getCurrentMemberId();
		final VoteDetail detail = voteReader.readDetail(voteId, memberId);

		return VoteGetServiceResponse.from(detail);
	}

	public CursorSummary<VoteSummary> getVotesByCursor(
		final Hobby hobby,
		final VoteStatusCondition statusCondition,
		final VoteSortCondition sortCondition,
		final CursorPageParameters parameters
	) {
		final Long memberId = memberUtils.getCurrentMemberId();

		if (memberId == null && statusCondition != null && statusCondition.isRequiredLogin()) {
			throw new BusinessException(ErrorCode.UNAUTHORIZED);
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

	public VoteGetByKeywordServiceResponse getVotesByKeyword(
		final String keyword,
		final CursorPageParameters parameters
	) {
		if (keyword.isBlank()) {
			return new VoteGetByKeywordServiceResponse(
				new CursorSummary<>(null, 0, Collections.emptyList()), 0
			);
		}

		final CursorSummary<VoteSummary> cursorSummary = voteReader.readByCursor(
			null,
			null,
			VoteSortCondition.RECENT,
			keyword,
			parameters,
			null
		);
		final long totalVoteCount = voteReader.countByKeyword(keyword); // 키워드를 가진 아이템 쿼리 두 번 나감 -> 리팩토링

		return new VoteGetByKeywordServiceResponse(cursorSummary, totalVoteCount);
	}

	public List<VoteRankingInfo> rankVote(final Hobby hobby) {
		return voteRedisManager.getRanking(hobby.toString());
	}

	private void validateItemIds(
		final Long item1Id,
		final Long item2Id
	) {
		if (item1Id.equals(item2Id)) {
			throw new BusinessException(ErrorCode.VOTE_ITEM_DUPLICATED);
		}

		if (itemReader.doesNotExist(item1Id) || itemReader.doesNotExist(item2Id)) {
			throw new EntityNotFoundException(ErrorCode.ITEM_NOT_FOUND);
		}
	}

	private VoteRankingInfo getVoteRedis(final Vote vote) {
		final Item item1 = itemReader.read(vote.getItem1Id());
		final Item item2 = itemReader.read(vote.getItem2Id());

		return VoteRankingInfo.builder()
			.id(Long.MAX_VALUE - vote.getId())
			.item1Image(item1.getImage())
			.item2Image(item2.getImage())
			.build();
	}
}
