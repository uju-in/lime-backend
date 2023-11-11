package com.programmers.bucketback.domains.vote.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.common.vo.CursorPageParameters;
import com.programmers.bucketback.domains.item.application.ItemReader;
import com.programmers.bucketback.domains.item.application.vo.ItemInfo;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.vote.application.dto.response.GetVoteServiceResponse;
import com.programmers.bucketback.domains.vote.application.dto.response.GetVotesServiceResponse;
import com.programmers.bucketback.domains.vote.domain.Vote;
import com.programmers.bucketback.domains.vote.domain.Voter;
import com.programmers.bucketback.domains.vote.repository.VoteRepository;
import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.EntityNotFoundException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VoteReader {

	private final VoteCounter voteCounter;
	private final VoteRepository voteRepository;
	private final VoterReader voterReader;
	private final ItemReader itemReader;

	@Transactional(readOnly = true)
	public Vote read(final Long voteId) {
		return voteRepository.findById(voteId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.VOTE_NOT_FOUND));
	}

	@Transactional(readOnly = true)
	public GetVoteServiceResponse read(
		final Long voteId,
		final Long memberId
	) {
		final Vote vote = read(voteId);
		final Long item1Id = vote.getItem1Id();
		final Long item2Id = vote.getItem2Id();
		final ItemInfo item1Info = getItemInfo(item1Id);
		final ItemInfo item2Info = getItemInfo(item2Id);

		final int item1Votes = voteCounter.count(vote, item1Id);
		final int item2Votes = voteCounter.count(vote, item2Id);
		final VoteInfo voteInfo = VoteInfo.of(vote, item1Votes, item2Votes);

		final boolean isOwner = isOwner(vote, memberId);
		final Long selectedItemId = getSelectedItemId(vote, memberId);

		return GetVoteServiceResponse.builder()
			.item1Info(item1Info)
			.item2Info(item2Info)
			.voteInfo(voteInfo)
			.isOwner(isOwner)
			.selectedItemId(selectedItemId)
			.build();
	}

	public GetVotesServiceResponse readByCursor(
		final Hobby hobby,
		final VoteStatusCondition statusCondition,
		final String sortCondition,
		final CursorPageParameters parameters,
		final Long memberId
	) {
		final int pageSize = parameters.size() == null ? 20 : parameters.size();

		final VoteSortCondition voteSortCondition = getVoteSortCondition(statusCondition, sortCondition);

		if (memberId == null && statusCondition.isRequiredLogin()) {
			return new GetVotesServiceResponse(null, Collections.emptyList());
		}

		final List<VoteSummary> voteSummaries = voteRepository.findAllByCursor(
			hobby,
			statusCondition,
			voteSortCondition,
			memberId,
			parameters.cursorId(),
			pageSize
		);

		final List<VoteCursorSummary> voteCursorSummaries = getVoteCursorSummaries(voteSummaries);
		final String nextCursorId = getNextCursorId(voteSummaries);

		return new GetVotesServiceResponse(nextCursorId, voteCursorSummaries);
	}

	private ItemInfo getItemInfo(final Long itemId) {
		final Item item = itemReader.read(itemId);

		return ItemInfo.from(item);
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

	private VoteSortCondition getVoteSortCondition(
		final VoteStatusCondition statusCondition,
		final String sortCondition
	) {
		if (Objects.equals(sortCondition, "popularity")) {
			if (statusCondition == VoteStatusCondition.COMPLETED) {
				return VoteSortCondition.POPULARITY;
			}

			throw new BusinessException(ErrorCode.VOTE_BAD_POPULARITY);
		}

		return VoteSortCondition.RECENT;
	}

	private List<VoteCursorSummary> getVoteCursorSummaries(final List<VoteSummary> voteSummaries) {
		final List<VoteCursorSummary> voteCursorSummaries = new ArrayList<>();
		for (final VoteSummary voteSummary : voteSummaries) {
			final Long item1Id = voteSummary.item1Id();
			final Item item1 = itemReader.read(item1Id);
			final Long item2Id = voteSummary.item2Id();
			final Item item2 = itemReader.read(item2Id);

			voteCursorSummaries.add(VoteCursorSummary.of(voteSummary, item1, item2));
		}

		return voteCursorSummaries;
	}

	private String getNextCursorId(final List<VoteSummary> voteSummaries) {
		final int votesSize = voteSummaries.size();
		return votesSize == 0 ? null : voteSummaries.get(votesSize - 1).cursorId();
	}
}
