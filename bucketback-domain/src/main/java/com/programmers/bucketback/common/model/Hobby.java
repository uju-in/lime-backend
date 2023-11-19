package com.programmers.bucketback.common.model;

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
public enum Hobby {

	BASKETBALL("농구", "basketball"),
	BASEBALL("야구", "baseball"),
	SOCCER("축구", "soccer"),
	CYCLE("사이클", "cycle"),
	KEYBOARD("키보드", "keyboard"),
	SWIMMING("수영", "swimming");

	private static final Map<String, Hobby> HOBBY_NAME_MAP;

	private static final Map<String, Hobby> HOBBY_VALUE_MAP;

	static {
		HOBBY_NAME_MAP = Collections.unmodifiableMap(Stream.of(values())
			.collect(Collectors.toMap(Hobby::getName, Function.identity())));

		HOBBY_VALUE_MAP = Collections.unmodifiableMap(Stream.of(values())
			.collect(Collectors.toMap(Hobby::getHobbyValue, Function.identity())));
	}

	private final String hobbyValue;
	private final String name;

	public static Hobby fromName(final String name) {
		String nameLowerCase = name.toLowerCase();
		if (HOBBY_NAME_MAP.containsKey(nameLowerCase)) {
			return HOBBY_NAME_MAP.get(nameLowerCase);
		}

		throw new BusinessException(ErrorCode.HOBBY_BAD_PARAMETER);
	}

	public static Hobby fromHobbyValue(final String hobbyValue) {
		if (HOBBY_VALUE_MAP.containsKey(hobbyValue)) {
			return HOBBY_VALUE_MAP.get(hobbyValue);
		}

		throw new BusinessException(ErrorCode.HOBBY_BAD_PARAMETER);
	}
}
