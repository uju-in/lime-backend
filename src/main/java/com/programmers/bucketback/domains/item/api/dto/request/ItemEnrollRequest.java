package com.programmers.bucketback.domains.item.api.dto.request;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.item.application.dto.ItemEnrollServiceRequest;
import com.programmers.bucketback.global.annotation.Enum;

import jakarta.validation.constraints.NotNull;

public record ItemEnrollRequest(
	@Enum(message = "옳바르지 않은 hobby enum 입니다.")
	Hobby hobby,

	@NotNull(message = "아이템 URL을 입력하지 않았습니다.")
	String itemUrl
) {
	public ItemEnrollServiceRequest toEnrollItemServiceRequest() {
		return new ItemEnrollServiceRequest(hobby, itemUrl);
	}
}
