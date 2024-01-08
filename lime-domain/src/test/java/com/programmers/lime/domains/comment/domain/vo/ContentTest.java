package com.programmers.lime.domains.comment.domain.vo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.programmers.lime.error.BusinessException;

class ContentTest {

	@ParameterizedTest
	@DisplayName("댓글 내용 유효한 값의 길이 검증 성공")
	@ValueSource(ints = {1, 10, 300})
	void successValidateContentLength(int count) {
		String fill = "a";
		Content content = new Content(fill.repeat(count));
		assertThat(content).isNotNull();
	}

	@ParameterizedTest
	@DisplayName("댓글 내용 유효하지 않은 값 예외 검증 성공")
	@ValueSource(ints = {301, 0})
	void successValidateInvalidContentLength(int count) {
		String fill = "a";
		assertThatThrownBy(() -> new Content(fill.repeat(count)))
			.isInstanceOf(BusinessException.class);
	}

	@ParameterizedTest
	@DisplayName("빈 내용과 null 값에 대한 예외 성공")
	@NullAndEmptySource
	void successValidateEmptyAndNullContent(String fill) {
		assertThatThrownBy(() -> new Content(fill))
			.isInstanceOf(BusinessException.class);
	}
}