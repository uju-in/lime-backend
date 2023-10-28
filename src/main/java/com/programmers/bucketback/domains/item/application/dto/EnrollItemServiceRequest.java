package com.programmers.bucketback.domains.item.application.dto;

import com.programmers.bucketback.domains.common.Hobby;

import jakarta.validation.constraints.NotNull;

public record EnrollItemServiceRequest(
	@NotNull
	Hobby hobby,

	@NotNull
	String itemUrl
) {
}
