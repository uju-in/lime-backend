package com.programmers.bucketback.domains.member.api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberUpdatePasswordRequest(
		@Size(min = 6, max = 15, message = "비밀번호는 6글자에서 10글자 사이여야 합니다.")
		@Pattern(regexp = "^(?=.*[!@#\\$%\\^&*\\(\\)\\-_+=\\[\\]{};:'\",.<>?/`~])(?=.*[A-Za-z])(?=.*\\d).*$", message = "비밀번호는 영어, 숫자, 특수문자가 최소 1개씩 포함되야합니다.")
		@NotNull(message = "비밀번호는 필수 값입니다.")
		String password
) {
}
