package com.programmers.bucketback.domains.vote.repository;

import java.util.List;

import com.programmers.bucketback.Hobby;
import com.programmers.bucketback.domains.vote.model.request.VoteSortCondition;
import com.programmers.bucketback.domains.vote.model.request.VoteStatusCondition;
import com.programmers.bucketback.domains.vote.model.response.VoteSummary;

public interface VoteRepositoryForCursor {
	List<VoteSummary> findAllByCursor(
		final Hobby hobby,
		final VoteStatusCondition statusCondition,
		final VoteSortCondition sortCondition,
		final Long memberId,
		final String nextCursorId,
		final int pageSize
	);
}
