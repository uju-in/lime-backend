package com.programmers.bucketback.domains.bucket.application.vo;

import java.util.List;

public record BucketMemberItemCursorSummary(
	String nextCursorId,
	int summaryCount,
	List<BucketMemberItemSummary> summaries
) {

}

