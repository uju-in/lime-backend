package com.programmers.bucketback.common.cursor;

import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CursorUtils {

	public static <T extends CursorIdParser> CursorSummary<T> getCursorSummaries(final List<T> summaries) {
		if (summaries.isEmpty()) {
			return new CursorSummary<>(null, 0, summaries);
		}

		T lastSummary = summaries.get(summaries.size() - 1);
		String nextCursorId = lastSummary.cursorId();

		int summaryCount = summaries.size();

		return new CursorSummary<>(nextCursorId, summaryCount, summaries);
	}
}
