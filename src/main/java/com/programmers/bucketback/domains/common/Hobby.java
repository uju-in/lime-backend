package com.programmers.bucketback.domains.common;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Hobby {

	BASKETBALL("농구"),
	BASEBALL("야구"),
	SOCCER("축구"),
	CYCLE("사이클"),
	KEYBOARD("키보드"),
	SWIMMING("수영");

	@JsonValue
	final private String hobbyValue;

	Hobby(final String hobbyValue) {
		this.hobbyValue = hobbyValue;
	}

	@JsonCreator
	public static Hobby fromEventStatus(final String hobbyValue) {
		return Arrays.stream(values())
			.filter(type -> type.hobbyValue.equals(hobbyValue))
			.findAny()
			.orElse(null);
	}
}
