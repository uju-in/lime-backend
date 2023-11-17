package com.programmers.bucketback.domains.vote.domain.vo;

import java.util.Objects;

import com.programmers.bucketback.error.exception.BusinessException;
import com.programmers.bucketback.error.exception.ErrorCode;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Content {

	public static final int MAX_CONTENT_LENGTH = 1000;

	@Column(name = "content", nullable = false, length = MAX_CONTENT_LENGTH)
	private String content;

	public Content(final String content) {
		validate(content);
		this.content = Objects.requireNonNull(content);
	}

	private void validate(final String content) {
		if (content.length() > MAX_CONTENT_LENGTH) {
			throw new BusinessException(ErrorCode.VOTE_CONTENT_BAD_LENGTH);
		}
	}
}
