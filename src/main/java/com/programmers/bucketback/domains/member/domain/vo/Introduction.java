package com.programmers.bucketback.domains.member.domain.vo;

import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Introduction {

	public static final int MAX_INTRODUCTION_LENGTH = 300;

	@Column(name = "introduction", length = MAX_INTRODUCTION_LENGTH)
	private String introduction;

	public Introduction(final String introduction) {
		validateLength(introduction);
		this.introduction = introduction;
	}

	private void validateLength(final String introduction) {
		if (introduction.length() > MAX_INTRODUCTION_LENGTH) {
			throw new BusinessException(ErrorCode.MEMBER_INTRODUCTION_BAD_LENGTH);
		}
	}
}
