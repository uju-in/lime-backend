package com.programmers.bucketback.domains.member.domain.vo;

import java.util.regex.Pattern;

import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class LoginInfo {

	public static final int MIN_PASSWORD_LENGTH = 6;
	public static final int MAX_PASSWORD_LENGTH = 15;
	public static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[!@#\\$%\\^&*\\(\\)\\-_+=\\[\\]{};:'\",.<>?/`~])(?=.*[A-Za-z])(?=.*\\d).*$");

	@NotNull
	@Column(name = "email")
	private String email;

	@NotNull
	@Column(name = "password")
	private String password;

	public LoginInfo(
		final String email,
		final String password
	) {
		this.email = email;
		this.password = password;
	}

	public static void validatePassword(final String password) {
		validatePasswordLength(password);
		validatePasswordPattern(password);
	}

	private static void validatePasswordLength(final String password) {
		if (MIN_PASSWORD_LENGTH > password.length() || password.length() > MAX_PASSWORD_LENGTH) {
			throw new BusinessException(ErrorCode.MEMBER_PASSWORD_BAD_LENGTH);
		}
	}

	private static void validatePasswordPattern(final String password) {
		if (!PASSWORD_PATTERN.matcher(password).matches()) {
			throw new BusinessException(ErrorCode.MEMBER_PASSWORD_BAD_PATTERN);
		}
	}

	public void updatePassword(final String password) {
		this.password = password;
	}
}
