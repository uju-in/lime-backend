package com.programmers.lime.global.cursor;

import org.springdoc.core.annotations.ParameterObject;

import com.programmers.lime.common.cursor.CursorPageParameters;

import io.swagger.v3.oas.annotations.Parameter;

@ParameterObject
public record CursorRequest(
	@Parameter(description = "조회 할 커서 id", example = "null")
	String cursorId,

	@Parameter(description = "조회 할 페이지 사이즈", example = "10")
	Integer size
) {
	public CursorPageParameters toParameters() {
		return new CursorPageParameters(cursorId, size);
	}
}
