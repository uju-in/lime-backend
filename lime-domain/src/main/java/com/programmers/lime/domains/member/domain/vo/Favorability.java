package com.programmers.lime.domains.member.domain.vo;

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
public enum Favorability {
	AVERAGE("보통"),
	INTERESTED("관심"),
	FRIENDLY("우호");

	private static final Map<String, Favorability> FAVORABILITY_MAP;

	static {
		FAVORABILITY_MAP = Collections.unmodifiableMap(Stream.of(values())
			.collect(Collectors.toMap(Favorability::getName, Function.identity())));
	}

	private final String name;

	public static Favorability from(final String name) {
		if (FAVORABILITY_MAP.containsKey(name)) {
			return FAVORABILITY_MAP.get(name);
		}

		throw new BusinessException(ErrorCode.MEMBER_INTRODUCTION_FAVORABILITY_BAD_REQUEST);
	}
}
