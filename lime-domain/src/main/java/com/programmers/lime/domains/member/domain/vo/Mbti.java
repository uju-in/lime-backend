package com.programmers.lime.domains.member.domain.vo;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;

public enum Mbti {
	ISFP,
	ISFJ,
	ISTP,
	ISTJ,
	INFP,
	INFJ,
	INTP,
	INTJ,
	ESFP,
	ESFJ,
	ESTP,
	ESTJ,
	ENFP,
	ENFJ,
	ENTP,
	ENTJ;

	private static final Map<String, Mbti> MBTI_MAP;

	static {
		MBTI_MAP = Collections.unmodifiableMap(Stream.of(values())
			.collect(Collectors.toMap(Mbti::name, Function.identity())));
	}

	public static Mbti from(final String name) {
		if (MBTI_MAP.containsKey(name)) {
			return MBTI_MAP.get(name);
		}

		throw new BusinessException(ErrorCode.MEMBER_INTRODUCTION_MBTI_BAD_REQUEST);
	}
}
