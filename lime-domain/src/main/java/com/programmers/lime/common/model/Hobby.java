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
	// Sports
	BASKETBALL("농구"),
	BASEBALL("야구"),
	BADMINTON("배드민턴"),
	WORK_OUT("헬스"),
	CLIMBING("클라이밍"),

	// Life
	PAINTING("드로잉"),
	MUSIC("음악"),
	COOKING("쿠킹"),
	GAME("게임"),
	DESKTERIOR("데스크테리어"),
	;

	private static final Map<String, Hobby> HOBBY_NAME_MAP;

	static {
		HOBBY_NAME_MAP = Collections.unmodifiableMap(Stream.of(values())
			.collect(Collectors.toMap(Hobby::getName, Function.identity())));
	}

	private final String name;

	public static Hobby from(final String name) {
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
