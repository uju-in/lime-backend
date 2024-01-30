package com.programmers.lime.domains.review.model;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;

public enum ReviewSortCondition {
	NEWEST,
	LOWEST_RATE,
	HIGHEST_RATE,
	LIKE_COUNT_DESC;

	private static final Map<String, ReviewSortCondition> SORT_CONDITION_MAP;

	static {
		SORT_CONDITION_MAP = Collections.unmodifiableMap(
			Stream.of(ReviewSortCondition.values())
				.collect(Collectors.toMap(ReviewSortCondition::name, Function.identity()))
		);
	}

	public static ReviewSortCondition from(String sortCondition) {
		if (sortCondition == null) {
			return null;
		}

		if (SORT_CONDITION_MAP.containsKey(sortCondition)) {
			return SORT_CONDITION_MAP.get(sortCondition);
		}

		throw new BusinessException(ErrorCode.REVIEW_BAD_SORT_CONDITION);
	}
}
