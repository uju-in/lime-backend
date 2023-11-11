package com.programmers.bucketback.domains.vote.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
import com.programmers.bucketback.global.error.exception.BusinessException;
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
		final String sortCondition,
		final CursorPageParameters parameters
	) {
		final int pageSize = parameters.size() == null ? 20 : parameters.size();

		final VoteSortCondition voteSortCondition = getVoteSortCondition(statusCondition, sortCondition);

		Long memberId = null;
		if (MemberUtils.isLoggedIn()) {
			memberId = MemberUtils.getCurrentMemberId();
		}

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

	private VoteSortCondition getVoteSortCondition(
		final VoteStatusCondition statusCondition,
		final String sortCondition
	) {
		if (statusCondition != VoteStatusCondition.COMPLETED && Objects.equals(sortCondition, "popularity")) {
			throw new BusinessException(ErrorCode.VOTE_BAD_POPULARITY);
		}

		if (statusCondition == VoteStatusCondition.COMPLETED && Objects.equals(sortCondition, "popularity")) {
			return VoteSortCondition.POPULARITY;
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
