package com.programmers.lime.common.model;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;

import lombok.Getter;

@Getter
public enum FavoriteType {

	ITEM("item"),
	FOLDER("folder"),
	ALL("all");

	private final String type;

	private static final Map<String, FavoriteType> FAVORITE_TYPE_MAP;

	static {
		FAVORITE_TYPE_MAP = Collections.unmodifiableMap(Stream.of(values())
			.collect(Collectors.toMap(FavoriteType::getType, Function.identity())));
	}

	FavoriteType(String type) {
		this.type = type;
	}

	public static FavoriteType from(final String type) {

		if(type == null) {
			return ALL;
		}

		String typeLowerCase = type.toLowerCase();
		if (FAVORITE_TYPE_MAP.containsKey(typeLowerCase)) {
			return FAVORITE_TYPE_MAP.get(typeLowerCase);
		}

		throw new BusinessException(ErrorCode.FAVORITE_TYPE_BAD_REQUEST);
	}
}
