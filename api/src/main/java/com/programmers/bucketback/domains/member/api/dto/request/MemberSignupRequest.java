package com.programmers.bucketback.domains.member.api.dto.request;

import com.programmers.bucketback.domains.member.domain.vo.LoginInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberSignupRequest(
		@Schema(description = "이메일", example = "test@test.com")
		@Email(message = "이메일 형식이 아닙니다.")
		@NotNull(message = "이메일은 필수 값입니다.")
		String email,

		@Schema(description = "비밀번호", example = "TestPasswoRd!123")
		@Size(min = 6, max = 15, message = "비밀번호는 6글자에서 15글자 사이여야 합니다.")
		@Pattern(regexp = "^(?=.*[!@#\\$%\\^&*\\(\\)\\-_+=\\[\\]{};:'\",.<>?/`~])(?=.*[A-Za-z])(?=.*\\d).*$", message = "비밀번호는 영어, 숫자, 특수문자가 최소 1개씩 포함되야합니다.")
		@NotNull(message = "비밀번호는 필수 값입니다.")
		String password,

		@Schema(description = "닉네임", example = "best_kim")
		@Size(min = 3, max = 5, message = "닉네임은 3글자에서 25글자 사이여야 합니다.")
		@Pattern(regexp = "^[A-Za-z0-9_]+$", message = "닉네임은 영어 대소문자, 숫자 그리고 언더스코어만 허용합니다.")
		@NotNull(message = "닉네임은 필수 값입니다.")
		String nickname
) {
	public LoginInfo toLoginInfo() {
		return new LoginInfo(email, password);
	}
}
