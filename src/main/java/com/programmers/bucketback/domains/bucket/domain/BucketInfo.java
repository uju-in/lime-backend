package com.programmers.bucketback.domains.bucket.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BucketInfo {

	@NotNull
	@Column(name = "name")
	private String name;

	@Column(name = "budget")
	private Integer budget;

	@Builder
	public BucketInfo(
		String name,
		Integer budget
	){
		this.name = name;
		this.budget = budget;
	}


}
