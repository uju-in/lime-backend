package com.programmers.bucketback.domains.bucket.domain;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BucketInfo {

	private static final int MIN_BUCKET_NAME_LENGTH = 1;
	private static final int MAX_BUCKET_NAME_LENGTH = 25;
	private static final int MIN_BUDGET = 0;

	@NotNull
	@Column(name = "hobby")
	@Enumerated(EnumType.STRING)
	private Hobby hobby;

	@NotNull
	@Column(name = "name")
	private String name;

	@NotNull
	@Column(name = "budget")
	private Integer budget;

	public BucketInfo(
		final Hobby hobby,
		final String name,
		final Integer budget
	) {
		validateBucketName(name);
		validateBucketBudget(budget);
		this.hobby = hobby;
		this.name = name;
		this.budget = budget;
	}

	public void validateBucketName(String name) {
		if (name.length() < MIN_BUCKET_NAME_LENGTH ||
			name.length() > MAX_BUCKET_NAME_LENGTH) {
			throw new BusinessException(ErrorCode.BUCKET_INVALID_NAME);
		}
	}

	public void validateBucketBudget(Integer budget) {
		if (budget != null && budget > MIN_BUDGET) {
			throw new BusinessException(ErrorCode.BUCKET_INVALID_BUDGET);
		}
	}

}
