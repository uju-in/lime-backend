package com.programmers.bucketback.domains.feed.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.error.BusinessException;

class FeedContentTest {

	@ParameterizedTest
	@ValueSource(ints = {0, 1, 1000})
	@DisplayName("피드 내용 생성 성공")
	void successCreateFeedContent(int count) {
		//given
		String feedContent = "a".repeat(count);
		//when
		Feed feed = new Feed(
			1L,
			Hobby.BASKETBALL,
			feedContent,
			"bucket1",
			10000
		);
		//then
		assertThat(feed).isNotNull();
	}

	@ParameterizedTest
	@ValueSource(ints = {1001, 10000, 100000})
	@DisplayName("피드 내용 길이 제한 초과로 실패")
	void failCreateFeedContentByLimitLength(int count) {
		//given
		String feedContent = "a".repeat(count);
		//then
		assertThatThrownBy(() -> new Feed(
			1L,
			Hobby.BASKETBALL,
			feedContent,
			"bucket1",
			10000
		))
			.isInstanceOf(BusinessException.class);
	}

	@Test
	@DisplayName("피드 내용 길이 null 입력으로 실패")
	void failCreateFeedContentByNull() {
		//then
		assertThatThrownBy(() -> new Feed(
			1L,
			Hobby.BASKETBALL,
			null,
			"bucket1",
			10000
		))
			.isInstanceOf(BusinessException.class);
	}
}