package com.programmers.bucketback.domains.common;

import java.util.List;

public class CursorUtils {

	public static CursorSummary getCursorSummaries(List<? extends CursorIdParser> summaries) {
		String nextCursorId = summaries.isEmpty() ? null : summaries.get(summaries.size() - 1).cursorId();

		int summaryCount = summaries.size();

		return new CursorSummary(nextCursorId, summaryCount, summaries);
	}
}