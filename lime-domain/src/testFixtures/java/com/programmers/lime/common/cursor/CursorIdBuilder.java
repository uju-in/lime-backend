package com.programmers.lime.common.cursor;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CursorIdBuilder {

	public static String build(Long id) {
		return "202301010000000000000" + id;
	}
}
