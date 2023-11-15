package com.programmers.bucketback.domains.member.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record MemberCheckEmailRequest(
		@Email(message = "이메일 형식이 아닙니다.")
		@NotNull(message = "이메일은 필수 값입니다.")
		String email
) {
}
