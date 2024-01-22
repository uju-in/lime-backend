package com.programmers.lime.domains.member.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberCheckNicknameRequest(
	@Schema(description = "닉네임", example = "best_kim")
	@Size(min = 3, max = 25, message = "닉네임은 3글자에서 25글자 사이여야 합니다.")
	@Pattern(regexp = "^[A-Za-z0-9_가-힣]+$", message = "닉네임은 영어 대소문자, 한글, 숫자 그리고 언더스코어만 허용합니다.")
	@NotNull(message = "닉네임은 필수 값입니다.")
	String nickname
) {
}
