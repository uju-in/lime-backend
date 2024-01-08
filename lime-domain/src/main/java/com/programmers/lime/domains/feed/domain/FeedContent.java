package com.programmers.lime.domains.feed.domain;

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
public class FeedContent {

	private static final int MAX_FEED_CONTENT_LENGTH = 1000;

	@Column(name = "content", length = MAX_FEED_CONTENT_LENGTH, nullable = false)
	private String content;

	public FeedContent(final String content) {
		validate(content);
		this.content = content;
	}

	private void validate(final String content) {
		if (content == null || content.length() > MAX_FEED_CONTENT_LENGTH) {
			throw new BusinessException(ErrorCode.COMMENT_CONTENT_BAD_LENGTH);
		}
	}
}
