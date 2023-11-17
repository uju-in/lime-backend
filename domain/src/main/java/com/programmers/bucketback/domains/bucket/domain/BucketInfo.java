package com.programmers.bucketback.domains.bucket.domain;

import java.util.Objects;

import com.programmers.bucketback.Hobby;
import com.programmers.bucketback.error.exception.BusinessException;
import com.programmers.bucketback.error.exception.ErrorCode;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

	@Column(name = "hobby")
	@Enumerated(EnumType.STRING)
	private Hobby hobby;

	@Column(name = "name")
	private String name;

	@Column(name = "budget")
	private Integer budget;

	public BucketInfo(
		final Hobby hobby,
		final String name,
		final Integer budget
	) {
		validateBucketName(name);
		validateBucketBudget(budget);
		this.hobby = Objects.requireNonNull(hobby);
		this.name = Objects.requireNonNull(name);
		this.budget = budget;
	}

	public void validateBucketName(final String name) {
		if (name.length() < MIN_BUCKET_NAME_LENGTH ||
			name.length() > MAX_BUCKET_NAME_LENGTH) {
			throw new BusinessException(ErrorCode.BUCKET_INVALID_NAME);
		}
	}

	public void validateBucketBudget(final Integer budget) {
		if (budget != null && budget < MIN_BUDGET) {
			throw new BusinessException(ErrorCode.BUCKET_INVALID_BUDGET);
		}
	}

	public void validateBucketBudget(
		final int totalPrice,
		final int bucketBudget
	) {
		if (totalPrice > bucketBudget) {
			throw new BusinessException(ErrorCode.BUCKET_EXCEED_BUDGET);
		}
	}

}
