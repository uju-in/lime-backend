package com.programmers.bucketback.domains.vote.application;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.item.application.ItemReader;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.vote.application.dto.request.CreateVoteServiceRequest;
import com.programmers.bucketback.domains.vote.application.dto.response.GetVoteServiceResponse;
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
		final int option1Votes = voteCounter.count(vote, option1ItemId);
		final int option2Votes = voteCounter.count(vote, option2ItemId);

		return GetVoteServiceResponse.builder()
			.option1Item(OptionItem.from(item1))
			.option2Item(OptionItem.from(item2))
			.content(vote.getContent())
			.createAt(vote.getCreatedAt())
			.isVoting(isVoting(vote))
			.option1Votes(option1Votes)
			.option2Votes(option2Votes)
			.participants(option1Votes + option2Votes)
			.selectedItemId(getSelectedItemId(vote))
			.build();
	}

	private static boolean isVoting(final Vote vote) {
		return LocalDateTime.now().isAfter(vote.getEndTime());
	}

	private Long getSelectedItemId(final Vote vote) {
		Long selectedItemId = null;
		if (MemberUtils.isLoggedIn()) {
			final Long memberId = MemberUtils.getCurrentMemberId();
			final Voter voter = voterReader.read(vote, memberId);
			selectedItemId = voter.getItemId();
		}

		return selectedItemId;
	}
}
