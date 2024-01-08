package com.programmers.lime.domains.vote.repository;

import java.util.List;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.vote.model.VoteCursorSummary;
import com.programmers.lime.domains.vote.model.VoteSortCondition;
import com.programmers.lime.domains.vote.model.VoteStatusCondition;

public interface VoteRepositoryForCursor {
	List<VoteCursorSummary> findAllByCursor(
		final Hobby hobby,
		final VoteStatusCondition statusCondition,
		final VoteSortCondition sortCondition,
		final String keyword,
		final Long memberId,
		final String nextCursorId,
		final int pageSize
	);

	Long countByKeyword(final String keyword);
}
