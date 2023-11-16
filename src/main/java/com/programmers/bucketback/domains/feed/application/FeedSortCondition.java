package com.programmers.bucketback.domains.feed.application;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FeedSortCondition {
	POPULARITY("popularity"),
	RECENT("recent");

	private static final Map<String, FeedSortCondition> SORT_CONDITION_MAP;

	static {
		SORT_CONDITION_MAP = Collections.unmodifiableMap(Stream.of(values())
			.collect(Collectors.toMap(FeedSortCondition::name, Function.identity())));
	}

	private final String name;

	public static FeedSortCondition from(final String name) {
		if (name == null) {
			return RECENT;
		}

		if (SORT_CONDITION_MAP.containsKey(name)) {
			return SORT_CONDITION_MAP.get(name);
		}

		throw new BusinessException(ErrorCode.FEED_BAD_SORT_CONDITION);
	}
}
