package com.programmers.lime.domains.vote.model;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VoteStatusCondition {
	POSTED("posted"),
	PARTICIPATED("participated");

	private static final Map<String, VoteStatusCondition> STATUS_CONDITION_MAP;

	static {
		STATUS_CONDITION_MAP = Collections.unmodifiableMap(Stream.of(values())
			.collect(Collectors.toMap(VoteStatusCondition::getName, Function.identity())));
	}

	private final String name;

	public static VoteStatusCondition from(final String name) {
		if (name == null) {
			return null;
		}

		if (STATUS_CONDITION_MAP.containsKey(name)) {
			return STATUS_CONDITION_MAP.get(name);
		}

		throw new BusinessException(ErrorCode.VOTE_BAD_STATUS_CONDITION);
	}

	public boolean isRequiredLogin() {
		return this == PARTICIPATED;
	}
}