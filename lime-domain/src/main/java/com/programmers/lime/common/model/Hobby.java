package com.programmers.lime.common.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Hobby {

	BASKETBALL("농구"),
	BASEBALL("야구"),
	SOCCER("축구"),
	CYCLE("사이클"),
	KEYBOARD("키보드"),
	SWIMMING("수영");

	private static final Map<String, Hobby> HOBBY_NAME_MAP;

	static {
		HOBBY_NAME_MAP = Collections.unmodifiableMap(Stream.of(values())
			.collect(Collectors.toMap(Hobby::getName, Function.identity())));
	}

	private final String name;

	public static Hobby fromName(final String name) {
		if (Objects.isNull(name)) {
			return null;
		}

		if (HOBBY_NAME_MAP.containsKey(name)) {
			return HOBBY_NAME_MAP.get(name);
		}

		throw new BusinessException(ErrorCode.HOBBY_BAD_PARAMETER);
	}

	public static List<String> getHobbies() {
		return HOBBY_NAME_MAP.values()
			.stream()
			.map(Hobby::getName)
			.toList();
	}
}
