package com.programmers.bucketback.domains.vote.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.common.vo.CursorPageParameters;
import com.programmers.bucketback.domains.item.application.ItemReader;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.vote.application.dto.response.GetVotesServiceResponse;
import com.programmers.bucketback.domains.vote.domain.Vote;
import com.programmers.bucketback.domains.vote.repository.VoteRepository;
import com.programmers.bucketback.global.error.exception.EntityNotFoundException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VoteReader {

	private final VoteRepository voteRepository;
	private final ItemReader itemReader;

	@Transactional(readOnly = true)
	public Vote read(final Long voteId) {
		return voteRepository.findById(voteId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.VOTE_NOT_FOUND));
	}

	public GetVotesServiceResponse readByCursor(
		final Hobby hobby,
		final VoteStatusCondition statusCondition,
		VoteSortCondition sortCondition,
		final CursorPageParameters parameters
	) {
		final int pageSize = parameters.size() == null ? 20 : parameters.size();

		if (statusCondition != VoteStatusCondition.COMPLETED || sortCondition != VoteSortCondition.POPULARITY) {
			sortCondition = VoteSortCondition.RECENT;
		}

		Long memberId = null;
		if (MemberUtils.isLoggedIn()) {
			memberId = MemberUtils.getCurrentMemberId();
		}

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

	private List<VoteCursorSummary> getVoteCursorSummaries(final List<VoteSummary> voteSummaries) {
		final List<VoteCursorSummary> voteCursorSummaries = new ArrayList<>();
		for (final VoteSummary voteSummary : voteSummaries) {
			final Long option1ItemId = voteSummary.vote().getOption1ItemId();
			final Item item1 = itemReader.read(option1ItemId);
			final Long option2ItemId = voteSummary.vote().getOption2ItemId();
			final Item item2 = itemReader.read(option2ItemId);

			voteCursorSummaries.add(
				VoteCursorSummary.builder()
					.voteInfo(VoteInfo.from(voteSummary.vote()))
					.option1Item(OptionItem.from(item1))
					.option2Item(OptionItem.from(item2))
					.cursorId(voteSummary.cursorId())
					.build()
			);
		}

		return voteCursorSummaries;
	}

	private String getNextCursorId(final List<VoteSummary> voteSummaries) {
		final int votesSize = voteSummaries.size();
		return votesSize == 0 ? null : voteSummaries.get(votesSize - 1).cursorId();
	}
}
