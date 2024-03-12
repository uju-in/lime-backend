package com.programmers.lime.global.cursor;

import org.springdoc.core.annotations.ParameterObject;

import com.programmers.lime.common.cursor.CursorPageParameters;

import io.swagger.v3.oas.annotations.Parameter;

@ParameterObject
public record CursorRequest(
	@Parameter(description = "커서아이디, 첫 조회는 커서아이디 없는 요청입니다.", example = "2023110124000001")
	String cursorId,

	@Parameter(description = "페이징 사이즈입니다", example = "10")
	Integer size
) {
	public CursorPageParameters toParameters() {
		return new CursorPageParameters(cursorId, size);
	}
}
