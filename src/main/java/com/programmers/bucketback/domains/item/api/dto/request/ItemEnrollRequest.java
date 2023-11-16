package com.programmers.bucketback.domains.item.api.dto.request;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.item.application.dto.ItemEnrollServiceRequest;
import com.programmers.bucketback.global.annotation.Enum;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ItemEnrollRequest(

	@Schema(description = "취미", example = "농구")
	@Enum(message = "옳바르지 않은 hobby enum 입니다.")
	Hobby hobby,

	@Schema(description = "아이템 URL", example = "https://www.coupang.com/vp/products/5720604355?itemId=9567481661")
	@NotNull(message = "아이템 URL을 입력하지 않았습니다.")
	String itemUrl
) {
	public ItemEnrollServiceRequest toEnrollItemServiceRequest() {
		return new ItemEnrollServiceRequest(hobby, itemUrl);
	}
}
