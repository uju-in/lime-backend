package com.programmers.bucketback.domains.common;

import java.util.List;

public class CursorUtils {

	public static <T extends CursorIdParser> CursorSummary<T> getCursorSummaries(final List<T> summaries) {
		T lastSummary = summaries.isEmpty() ? null : summaries.get(summaries.size() - 1);
		String nextCursorId = lastSummary.cursorId();

		int summaryCount = summaries.size();

		return new CursorSummary(nextCursorId, summaryCount, summaries);
	}
}
