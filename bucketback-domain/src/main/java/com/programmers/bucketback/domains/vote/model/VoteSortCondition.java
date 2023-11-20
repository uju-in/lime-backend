package com.programmers.bucketback.domains.vote.model;

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
public enum VoteSortCondition {
	POPULARITY("popularity"),
	RECENT("recent");

	private static final Map<String, VoteSortCondition> SORT_CONDITION_MAP;

	static {
		SORT_CONDITION_MAP = Collections.unmodifiableMap(Stream.of(values())
			.collect(Collectors.toMap(VoteSortCondition::getName, Function.identity())));
	}

	private final String name;

	public static VoteSortCondition from(final String name) {
		if (name == null) {
			return RECENT;
		}

		if (SORT_CONDITION_MAP.containsKey(name)) {
			return SORT_CONDITION_MAP.get(name);
		}

		throw new BusinessException(ErrorCode.VOTE_BAD_SORT_CONDITION);
	}

	public boolean isImpossibleSort(final VoteStatusCondition statusCondition) {
		return this == VoteSortCondition.POPULARITY && statusCondition != VoteStatusCondition.COMPLETED;
	}
}
