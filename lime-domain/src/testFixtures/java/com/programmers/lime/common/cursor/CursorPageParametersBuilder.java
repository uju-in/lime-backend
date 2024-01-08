package com.programmers.lime.common.cursor;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CursorPageParametersBuilder {

	public static CursorPageParameters build() {
		return new CursorPageParameters(
			"2023010100000000000001",
			20
		);
	}

	public static CursorPageParameters buildWithNull() {
		return new CursorPageParameters(
			null,
			null
		);
	}
}
