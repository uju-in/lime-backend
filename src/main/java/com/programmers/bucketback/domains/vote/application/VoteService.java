package com.programmers.bucketback.domains.vote.application;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.common.vo.CursorPageParameters;
import com.programmers.bucketback.domains.item.application.ItemReader;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.vote.application.dto.request.CreateVoteServiceRequest;
import com.programmers.bucketback.domains.vote.application.dto.response.GetVoteServiceResponse;
import com.programmers.bucketback.domains.vote.application.dto.response.GetVotesServiceResponse;
import com.programmers.bucketback.domains.vote.domain.Vote;
import com.programmers.bucketback.domains.vote.domain.Voter;
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
	private final VoterReader voterReader;
	private final VoteCounter voteCounter;
	private final ItemReader itemReader;

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
			throw new BusinessException(ErrorCode.MEMBER_ANONYMOUS); // 더 핏한 예러코드로 바꿀 예정
		}
		final Long memberId = MemberUtils.getCurrentMemberId();

		voteManager.cancel(voteId, memberId);
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
		final Vote vote = voteReader.read(voteId);
		final Long option1ItemId = vote.getOption1ItemId();
		final Long option2ItemId = vote.getOption2ItemId();
		final Item item1 = itemReader.read(option1ItemId);
		final Item item2 = itemReader.read(option2ItemId);

		boolean isOwner = false;
		Long selectedItemId = null;
		if (MemberUtils.isLoggedIn()) {
			final Long memberId = MemberUtils.getCurrentMemberId();
			isOwner = isOwner(vote, memberId);
			selectedItemId = getSelectedItemId(vote, memberId);
		}

		final int option1Votes = voteCounter.count(vote, option1ItemId);
		final int option2Votes = voteCounter.count(vote, option2ItemId);
		final VoteInfo voteInfo = VoteInfo.of(vote, option1Votes, option2Votes);

		return GetVoteServiceResponse.builder()
			.option1Item(OptionItem.from(item1))
			.option2Item(OptionItem.from(item2))
			.voteInfo(voteInfo)
			.isOwner(isOwner)
			.selectedItemId(selectedItemId)
			.build();
	}

	public GetVotesServiceResponse getVotesByCursor(
		final Hobby hobby,
		final VoteStatusCondition statusCondition,
		final String sortCondition,
		final CursorPageParameters parameters
	) {
		return voteReader.readByCursor(hobby, statusCondition, sortCondition, parameters);
	}

	private boolean isOwner(
		final Vote vote,
		final Long memberId
	) {
		return vote.getMemberId().equals(memberId);
	}

	private Long getSelectedItemId(
		final Vote vote,
		final Long memberId
	) {
		final Optional<Voter> voter = voterReader.read(vote, memberId);

		return voter.map(Voter::getItemId)
			.orElse(null);
	}
}
