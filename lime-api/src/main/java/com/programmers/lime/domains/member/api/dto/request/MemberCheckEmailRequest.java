package com.programmers.lime.domains.member.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record MemberCheckEmailRequest(
		@Schema(description = "이메일", example = "test@test.com")
		@Email(message = "이메일 형식이 아닙니다.")
		@NotNull(message = "이메일은 필수 값입니다.")
		String email
) {
}
