package com.programmers.bucketback.domains.common;

import java.util.List;

public record CursorSummary(
	String nextCursorId,
	int summaryCount,
	List<? extends CursorIdParser> summaries
) {
}
