package com.programmers.bucketback.domains.comment.domain.vo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.programmers.bucketback.error.BusinessException;

class ContentTest {

	@ParameterizedTest
	@ValueSource(ints = {1, 10, 300})
	void successValidateContentLength(int count) {
		String fill = "a";
		Content content = new Content(fill.repeat(count));
		assertThat(content).isNotNull();
	}

	@ParameterizedTest
	@ValueSource(ints = {301, 0})
	void failValidateContentLength(int count) {
		String fill = "a";
		assertThatThrownBy(() -> new Content(fill.repeat(count)))
			.isInstanceOf(BusinessException.class);
	}

	@ParameterizedTest
	@NullAndEmptySource
	void failEmptyAndNullContent(String fill) {
		assertThatThrownBy(() -> new Content(fill))
			.isInstanceOf(BusinessException.class);
	}
}