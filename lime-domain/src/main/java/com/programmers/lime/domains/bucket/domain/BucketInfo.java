package com.programmers.lime.domains.bucket.domain;

import java.util.Objects;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;

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

	@Column(name = "hobby", nullable = false)
	@Enumerated(EnumType.STRING)
	private Hobby hobby;

	@Column(name = "name", nullable = false)
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
		if (
			name == null ||
				(name.length() < MIN_BUCKET_NAME_LENGTH || name.length() > MAX_BUCKET_NAME_LENGTH)
		) {
			throw new BusinessException(ErrorCode.BUCKET_INVALID_NAME);
		}
	}

	public void validateBucketBudget(final Integer budget) {
		if (budget == null || budget < MIN_BUDGET) {
			throw new BusinessException(ErrorCode.BUCKET_INVALID_BUDGET);
		}
	}
}
