package com.programmers.bucketback.domains.vote.model.request;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.programmers.bucketback.error.exception.BusinessException;
import com.programmers.bucketback.error.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VoteStatusCondition {
	INPROGRESS("inprogress"),
	COMPLETED("completed"),
	POSTED("posted"),
	PARTICIPATED("participated");

	private static final Map<String, VoteStatusCondition> STATUS_CONDITION_MAP;

	static {
		STATUS_CONDITION_MAP = Collections.unmodifiableMap(Stream.of(values())
			.collect(Collectors.toMap(VoteStatusCondition::getName, Function.identity())));
	}

	private final String name;

	public static VoteStatusCondition from(final String name) {
		if (STATUS_CONDITION_MAP.containsKey(name)) {
			return STATUS_CONDITION_MAP.get(name);
		}

		throw new BusinessException(ErrorCode.VOTE_BAD_STATUS_CONDITION);
	}

	public boolean isRequiredLogin() {
		return this == POSTED || this == PARTICIPATED;
	}
}