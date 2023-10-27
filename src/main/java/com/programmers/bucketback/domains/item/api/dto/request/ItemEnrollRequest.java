package com.programmers.bucketback.domains.item.api.dto.request;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.item.application.dto.ItemEnrollServiceRequest;
import com.programmers.bucketback.global.annotation.Enum;

import jakarta.validation.constraints.NotNull;

public record ItemEnrollRequest(
	@Enum
	Hobby hobby,

	@NotNull
	String itemUrl
) {
	public ItemEnrollServiceRequest toEnrollServiceRequest() {
		return new ItemEnrollServiceRequest(hobby, itemUrl);
	}
}
