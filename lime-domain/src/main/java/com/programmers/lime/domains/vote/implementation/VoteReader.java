package com.programmers.lime.domains.vote.implementation;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.common.cursor.CursorUtils;
import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.item.domain.Item;
import com.programmers.lime.domains.item.implementation.ItemReader;
import com.programmers.lime.domains.item.model.ItemInfo;
import com.programmers.lime.domains.vote.domain.Vote;
import com.programmers.lime.domains.vote.model.VoteCursorSummary;
import com.programmers.lime.domains.vote.model.VoteDetail;
import com.programmers.lime.domains.vote.model.VoteDetailInfo;
import com.programmers.lime.domains.vote.model.VoteSortCondition;
import com.programmers.lime.domains.vote.model.VoteStatusCondition;
import com.programmers.lime.domains.vote.model.VoteSummary;
import com.programmers.lime.domains.vote.repository.VoteRepository;
import com.programmers.lime.error.EntityNotFoundException;
import com.programmers.lime.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VoteReader {

	public static final int DEFAULT_PAGING_SIZE = 20;

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
	public VoteDetail readDetail(
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
		final VoteDetailInfo voteInfo = VoteDetailInfo.of(vote, item1Votes, item2Votes);

		final boolean isOwner = vote.isOwner(memberId);
		final Long selectedItemId = voterReader.readItemId(vote, memberId);

		return VoteDetail.builder()
			.item1Info(item1Info)
			.item2Info(item2Info)
			.voteInfo(voteInfo)
			.isOwner(isOwner)
			.selectedItemId(selectedItemId)
			.build();
	}

	@Transactional(readOnly = true)
	public CursorSummary<VoteSummary> readByCursor(
		final Hobby hobby,
		final VoteStatusCondition statusCondition,
		final VoteSortCondition sortCondition,
		final String keyword,
		final CursorPageParameters parameters,
		final Long memberId
	) {
		final int pageSize = getPageSize(parameters);

		final List<VoteCursorSummary> voteCursorSummaries = voteRepository.findAllByCursor(
			hobby,
			statusCondition,
			sortCondition,
			keyword,
			memberId,
			parameters.cursorId(),
			pageSize
		);

		final List<VoteSummary> voteSummaries = getVoteSummaries(voteCursorSummaries);

		return CursorUtils.getCursorSummaries(voteSummaries);
	}

	@Transactional(readOnly = true)
	public long countByKeyword(final String keyword) {
		return voteRepository.countByKeyword(keyword);
	}

	private int getPageSize(final CursorPageParameters parameters) {
		return parameters.size() == null ? DEFAULT_PAGING_SIZE : parameters.size();
	}

	private ItemInfo getItemInfo(final Long itemId) {
		final Item item = itemReader.read(itemId);

		return ItemInfo.from(item);
	}

	private List<VoteSummary> getVoteSummaries(final List<VoteCursorSummary> voteSummaries) {
		return voteSummaries.stream()
			.map(voteCursorSummary -> {
				final Long item1Id = voteCursorSummary.item1Id();
				final Item item1 = itemReader.read(item1Id);
				final Long item2Id = voteCursorSummary.item2Id();
				final Item item2 = itemReader.read(item2Id);

				return VoteSummary.of(voteCursorSummary, item1, item2);
			})
			.toList();
	}
}
