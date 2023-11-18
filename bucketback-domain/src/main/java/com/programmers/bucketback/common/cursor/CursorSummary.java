package com.programmers.bucketback.common.cursor;

import java.util.List;

public record CursorSummary<T extends CursorIdParser>(
	String nextCursorId,
	int summaryCount,
	List<T> summaries
) {
}
