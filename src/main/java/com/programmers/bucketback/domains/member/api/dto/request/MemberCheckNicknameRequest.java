package com.programmers.bucketback.domains.member.api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberCheckNicknameRequest(
		@Size(min = 3, max = 5, message = "닉네임은 3글자에서 25글자 사이여야 합니다.")
		@Pattern(regexp = "^[A-Za-z0-9_]+$", message = "닉네임은 영어 대소문자, 숫자 그리고 언더스코어만 허용합니다.")
		@NotNull(message = "닉네임은 필수 값입니다.")
		String nickname
) {
}
