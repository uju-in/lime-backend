package com.programmers.bucketback.domains.vote.domain.vo;

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
public class Content {

	public static final int MAX_CONTENT_LENGTH = 1000;

	@NotNull
	@Column(name = "content", length = MAX_CONTENT_LENGTH)
	private String content;

	public Content(@NotNull final String content) {
		validate(content);
		this.content = content;
	}

	private void validate(final String content) {
		if (content.length() > MAX_CONTENT_LENGTH) {
			throw new BusinessException(ErrorCode.VOTE_CONTENT_BAD_LENGTH);
		}
	}
}
