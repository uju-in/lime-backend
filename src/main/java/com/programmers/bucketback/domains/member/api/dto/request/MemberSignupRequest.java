package com.programmers.bucketback.domains.member.api.dto.request;

import com.programmers.bucketback.domains.member.domain.vo.LoginInfo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberSignupRequest(
		@Email(message = "이메일 형식이 아닙니다.")
		@NotNull(message = "이메일은 필수 값입니다.")
		String email,

		@Size(min = 6, max = 15, message = "비밀번호는 6글자에서 10글자 사이여야 합니다.")
		@Pattern(regexp = "^(?=.*[!@#\\$%\\^&*\\(\\)\\-_+=\\[\\]{};:'\",.<>?/`~])(?=.*[A-Za-z])(?=.*\\d).*$", message = "비밀번호는 영어, 숫자, 특수문자가 최소 1개씩 포함되야합니다.")
		@NotNull(message = "비밀번호는 필수 값입니다.")
		String password,

		@Size(min = 3, max = 5, message = "닉네임은 3글자에서 25글자 사이여야 합니다.")
		@Pattern(regexp = "^[A-Za-z0-9_]+$", message = "닉네임은 영어 대소문자, 숫자 그리고 언더스코어만 허용합니다.")
		@NotNull(message = "닉네임은 필수 값입니다.")
		String nickname
) {
	public LoginInfo toLoginInfo() {
		return new LoginInfo(email, password);
	}
}
