package com.programmers.lime.domains.item.repository;

import java.util.List;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.bucket.model.BucketMemberItemSummary;
import com.programmers.lime.domains.item.model.MemberItemSummary;

public interface MemberItemRepositoryForCursor {
	List<BucketMemberItemSummary> findBucketMemberItemsByCursor(
		final List<Long> itemIdsFromBucketItem,
		final List<Long> itemIdsFromMemberItem,
		final Hobby hobby,
		final Long memberId,
		final String cursorId,
		final int pageSize
	);

	List<MemberItemSummary> findMemberItemsByCursor(
		final Hobby hobby,
		final Long folderId,
		final Long memberId,
		final String cursorId,
		final int pageSize
	);
}
