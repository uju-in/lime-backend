package com.programmers.bucketback.domains.vote.repository;

import java.util.List;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.vote.application.VoteSortCondition;
import com.programmers.bucketback.domains.vote.application.VoteStatusCondition;
import com.programmers.bucketback.domains.vote.application.VoteSummary;

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
