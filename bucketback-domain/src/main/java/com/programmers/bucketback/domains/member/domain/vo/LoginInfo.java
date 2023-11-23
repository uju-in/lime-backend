package com.programmers.bucketback.domains.member.domain.vo;

import java.util.Objects;
import java.util.regex.Pattern;

import com.programmers.bucketback.error.BusinessException;
import com.programmers.bucketback.error.ErrorCode;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class LoginInfo {

	public static final int MIN_PASSWORD_LENGTH = 6;
	public static final int MAX_PASSWORD_LENGTH = 15;
	public static final Pattern EMAIL_PATTERN = Pattern.compile(
		"[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");
	public static final Pattern PASSWORD_PATTERN = Pattern.compile(
		"^(?=.*[!@#\\$%\\^&*\\(\\)\\-_+=\\[\\]{};:'\",.<>?/`~])(?=.*[A-Za-z])(?=.*\\d).*$");
	public static final String DELETE_EXPRESSION = "delete";

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	public LoginInfo(
		final String email,
		final String password
	) {
		validateEmail(email);
		this.email = Objects.requireNonNull(email);
		this.password = Objects.requireNonNull(password);
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

	private void validateEmail(final String email) {
		if (!EMAIL_PATTERN.matcher(email).matches()) {
			throw new BusinessException(ErrorCode.MEMBER_EMAIL_BAD_PATTERN);
		}
	}

	public void updatePassword(final String password) {
		this.password = password;
	}

	public void delete() {
		this.email += DELETE_EXPRESSION;
	}
}
