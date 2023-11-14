package com.programmers.bucketback.domains.vote.application;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.common.vo.CursorPageParameters;
import com.programmers.bucketback.domains.item.application.ItemReader;
import com.programmers.bucketback.domains.item.application.vo.ItemInfo;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.vote.application.dto.request.VoteSortCondition;
import com.programmers.bucketback.domains.vote.application.dto.request.VoteStatusCondition;
import com.programmers.bucketback.domains.vote.application.dto.response.*;
import com.programmers.bucketback.domains.vote.domain.Vote;
import com.programmers.bucketback.domains.vote.domain.Voter;
import com.programmers.bucketback.domains.vote.repository.VoteRepository;
import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.EntityNotFoundException;
import com.programmers.bucketback.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
	public VoteGetServiceResponse read(
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

		return VoteGetServiceResponse.builder()
			.item1Info(item1Info)
			.item2Info(item2Info)
			.voteInfo(voteInfo)
			.isOwner(isOwner)
			.selectedItemId(selectedItemId)
			.build();
	}

	@Transactional(readOnly = true)
	public GetVotesServiceResponse readByCursor(
		final Hobby hobby,
		final VoteStatusCondition statusCondition,
		final VoteSortCondition sortCondition,
		final CursorPageParameters parameters,
		final Long memberId
	) {
		if (memberId == null && statusCondition.isRequiredLogin()) {
			return new GetVotesServiceResponse(null, Collections.emptyList());
		}

		if (sortCondition == VoteSortCondition.POPULARITY && statusCondition != VoteStatusCondition.COMPLETED) {
			throw new BusinessException(ErrorCode.VOTE_CANNOT_SORT);
		}

		final int pageSize = parameters.size() == null ? 20 : parameters.size();

		final List<VoteSummary> voteSummaries = voteRepository.findAllByCursor(
			hobby,
			statusCondition,
			sortCondition,
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

	private List<VoteCursorSummary> getVoteCursorSummaries(final List<VoteSummary> voteSummaries) {
		return voteSummaries.stream()
			.map(voteSummary -> {
				final Long item1Id = voteSummary.item1Id();
				final Item item1 = itemReader.read(item1Id);
				final Long item2Id = voteSummary.item2Id();
				final Item item2 = itemReader.read(item2Id);

				return VoteCursorSummary.of(voteSummary, item1, item2);
			})
			.toList();
	}

	private String getNextCursorId(final List<VoteSummary> voteSummaries) {
		final int votesSize = voteSummaries.size();
		return votesSize == 0 ? null : voteSummaries.get(votesSize - 1).cursorId();
	}
}
