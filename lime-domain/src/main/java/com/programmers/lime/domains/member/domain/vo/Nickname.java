package com.programmers.lime.domains.member.domain.vo;

import java.util.Objects;
import java.util.regex.Pattern;

import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Nickname {

	public static final int MIN_NICKNAME_LENGTH = 3;
	public static final int MAX_NICKNAME_LENGTH = 25;
	public static final Pattern NICKNAME_PATTERN = Pattern.compile("^[A-Za-z0-9_가-힣]+$");

	@Column(name = "nickname", nullable = false, length = MAX_NICKNAME_LENGTH)
	private String nickname;

	public Nickname(final String nickname) {
		validate(nickname);
		this.nickname = Objects.requireNonNull(nickname);
	}

	private void validate(final String nickname) {
		validateLength(nickname);
		validatePattern(nickname);
	}

	private void validateLength(final String nickname) {
		if (MIN_NICKNAME_LENGTH > nickname.length() || nickname.length() > MAX_NICKNAME_LENGTH) {
			throw new BusinessException(ErrorCode.MEMBER_NICKNAME_BAD_LENGTH);
		}
	}

	private void validatePattern(final String nickname) {
		if (!NICKNAME_PATTERN.matcher(nickname).matches()) {
			throw new BusinessException(ErrorCode.MEMBER_NICKNAME_BAD_PATTERN);
		}
	}

	public void delete() {
		this.nickname = "";
	}
}
