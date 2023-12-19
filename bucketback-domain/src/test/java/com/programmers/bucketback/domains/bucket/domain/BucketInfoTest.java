package com.programmers.bucketback.domains.bucket.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.error.BusinessException;

class BucketInfoTest {

	static Stream<Arguments> createNullBucketInfo() {
		Integer budget = 1000;
		String name = "유러피안농구";

		return Stream.of(
			Arguments.of(null, null),
			Arguments.of(name, null),
			Arguments.of(null, budget),
			Arguments.of("", null)
		);
	}

	@ParameterizedTest
	@CsvSource({"1, 0", "10, 10000", "25, 2147483647"})
	@DisplayName("bucketInfo 생성 성공")
	void successCreateBucketInfo(
		final int count,
		final Integer budget
	) {
		//given
		String name = "a";
		//when
		BucketInfo bucketInfo = new BucketInfo(
			Hobby.BASKETBALL,
			name.repeat(count),
			budget
		);
		//then
		assertThat(bucketInfo).isNotNull();
	}

	@ParameterizedTest
	@CsvSource({"0, 0", "26, 2147483647", "25, -1"})
	@DisplayName("bucketInfo 생성 성공")
	void failCreateBucketInfo(
		final int count,
		final Integer budget
	) {
		//given
		String name = "a";
		//then
		assertThatThrownBy(() -> new BucketInfo(
			Hobby.BASKETBALL,
			name.repeat(count),
			budget
		))
			.isInstanceOf(BusinessException.class);
	}

	@ParameterizedTest
	@MethodSource("createNullBucketInfo")
	@DisplayName("bucketInfo null 문제로 생성 실패")
	void failCreateBucketInfoByNull(
		final String name,
		final Integer budget
	) {
		//then
		assertThatThrownBy(() -> new BucketInfo(
			Hobby.BASKETBALL,
			name,
			budget
		))
			.isInstanceOf(BusinessException.class);
	}
}