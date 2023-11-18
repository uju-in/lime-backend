package com.programmers.bucketback.domains.item.repository;

import java.util.List;

import com.programmers.bucketback.domains.bucket.model.BucketMemberItemSummary;

public interface MemberItemRepositoryForCursor {
	List<BucketMemberItemSummary> findBucketMemberItemsByCursor(
		final List<Long> itemIdsFromBucketItem,
		final List<Long> itemIdsFromMemberItem,
		final Long memberId,
		final String cursorId,
		final int pageSize
	);
}
