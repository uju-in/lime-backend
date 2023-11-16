package com.programmers.bucketback.domains.common;

import java.util.List;

public record CursorSummary<T extends CursorIdParser>(
	String nextCursorId,
	int summaryCount,
	List<T> summaries
) {
}
