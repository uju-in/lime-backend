package com.programmers.bucketback.domains.common;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.fasterxml.jackson.annotation.JsonValue;
import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Hobby {

	BASKETBALL("농구", "basketball"),
	BASEBALL("야구", "baseball"),
	SOCCER("축구", "soccer"),
	CYCLE("사이클", "cycle"),
	KEYBOARD("키보드", "keyboard"),
	SWIMMING("수영", "swimming");

	private static final Map<String, Hobby> HOBBY_MAP;

	static {
		HOBBY_MAP = Collections.unmodifiableMap(Stream.of(values())
			.collect(Collectors.toMap(Hobby::getName, Function.identity())));
	}

	@JsonValue
	private final String hobbyValue;
	private final String name;

	public static Hobby from(final String name) {
		if (HOBBY_MAP.containsKey(name)) {
			return HOBBY_MAP.get(name);
		}

		throw new BusinessException(ErrorCode.HOBBY_BAD_PARAMETER);
	}

	@JsonCreator
	public static Hobby fromEventStatus(final String hobbyValue) {
		return Arrays.stream(values())
			.filter(type -> type.hobbyValue.equals(hobbyValue))
			.findAny()
			.orElse(null);
	}
}
