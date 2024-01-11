package com.programmers.lime.domains.member.domain.vo;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Introduction {

	public static final int MAX_CONTENT_LENGTH = 300;
	public static final int MIN_CAREER = 0;

	@Enumerated(EnumType.STRING)
	@Column(name = "hobby", nullable = false)
	private Hobby hobby;

	@Column(name = "career", nullable = false)
	private int career;

	@Column(name = "favorability", nullable = false)
	private Favorability favorability;

	@Column(name = "content", nullable = false, length = MAX_CONTENT_LENGTH)
	private String content;

	@Enumerated(EnumType.STRING)
	@Column(name = "mbti", nullable = false)
	private Mbti mbti;

	@Builder
	private Introduction(
		final String hobby,
		final int career,
		final String favorability,
		final String content,
		final String mbti
	) {
		validate(content, career);
		this.hobby = Hobby.fromName(hobby);
		this.career = career;
		this.favorability = Favorability.from(favorability);
		this.content = content;
		this.mbti = Mbti.from(mbti);
	}

	private void validate(
		final String content,
		final int career
	) {
		validateContent(content);
		validateCareer(career);
	}

	private void validateContent(final String content) {
		if (content.length() > MAX_CONTENT_LENGTH) {
			throw new BusinessException(ErrorCode.MEMBER_INTRODUCTION_CONTENT_BAD_LENGTH);
		}
	}

	private void validateCareer(final int career) {
		if (career < MIN_CAREER) {
			throw new BusinessException(ErrorCode.MEMBER_INTRODUCTION_CAREER_BAD_REQUEST);
		}
	}
}
