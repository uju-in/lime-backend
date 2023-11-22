package com.programmers.bucketback.domains.feed.model;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.programmers.bucketback.error.BusinessException;
import com.programmers.bucketback.error.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FeedSortCondition {
	POPULARITY,
	RECENT;

	private static final Map<String, FeedSortCondition> SORT_CONDITION_MAP;

	static {
		SORT_CONDITION_MAP = Collections.unmodifiableMap(Stream.of(values())
			.collect(Collectors.toMap(FeedSortCondition::name, Function.identity())));
	}

	public static FeedSortCondition from(final String name) {
		if (name == null) {
			return RECENT;
		}

		String nameUpperCase = name.toUpperCase();
		if (SORT_CONDITION_MAP.containsKey(nameUpperCase)) {
			return SORT_CONDITION_MAP.get(nameUpperCase);
		}

		throw new BusinessException(ErrorCode.FEED_BAD_SORT_CONDITION);
	}
}
