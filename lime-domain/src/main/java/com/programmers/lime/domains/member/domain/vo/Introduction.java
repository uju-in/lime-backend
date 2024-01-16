package com.programmers.lime.domains.member.domain.vo;

import java.time.LocalDate;
import java.time.YearMonth;

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

	@Enumerated(EnumType.STRING)
	@Column(name = "hobby")
	private Hobby hobby;

	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "content", length = MAX_CONTENT_LENGTH)
	private String content;

	@Enumerated(EnumType.STRING)
	@Column(name = "mbti")
	private Mbti mbti;

	@Builder
	private Introduction(
		final String hobby,
		final YearMonth startDate,
		final String content,
		final String mbti
	) {
		validate(content);
		this.hobby = Hobby.from(hobby);
		this.startDate = startDate.atDay(1);
		this.content = content;
		this.mbti = Mbti.from(mbti);
	}

	private void validate(final String content) {
		if (content.length() > MAX_CONTENT_LENGTH) {
			throw new BusinessException(ErrorCode.MEMBER_INTRODUCTION_CONTENT_BAD_LENGTH);
		}
	}
}
