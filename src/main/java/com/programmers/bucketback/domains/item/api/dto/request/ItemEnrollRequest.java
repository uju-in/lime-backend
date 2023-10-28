package com.programmers.bucketback.domains.item.api.dto.request;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.item.application.dto.EnrollItemServiceRequest;
import com.programmers.bucketback.global.annotation.Enum;

import jakarta.validation.constraints.NotNull;

public record ItemEnrollRequest(
	@Enum
	Hobby hobby,

	@NotNull
	String itemUrl
) {
	public EnrollItemServiceRequest toEnrollItemServiceRequest() {
		return new EnrollItemServiceRequest(hobby, itemUrl);
	}
}
