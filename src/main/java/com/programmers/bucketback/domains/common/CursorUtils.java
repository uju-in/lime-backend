package com.programmers.bucketback.domains.common;

import java.util.List;

public class CursorUtils {

	public static <T extends CursorIdParser> CursorSummary<T> getCursorSummaries(final List<T> summaries) {
		String nextCursorId = summaries.isEmpty() ? null : summaries.get(summaries.size() - 1).cursorId();

		int summaryCount = summaries.size();

		return new CursorSummary(nextCursorId, summaryCount, summaries);
	}
}