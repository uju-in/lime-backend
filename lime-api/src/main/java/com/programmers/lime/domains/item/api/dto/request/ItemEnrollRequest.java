package com.programmers.lime.domains.item.api.dto.request;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.item.application.dto.ItemEnrollServiceRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ItemEnrollRequest(

	@Schema(description = "취미", example = "농구")
	String hobbyValue,

	@Schema(description = "아이템 URL", example = "https://www.coupang.com/vp/products/5720604355?itemId=9567481661")
	@NotNull(message = "아이템 URL을 입력하지 않았습니다.")
	String itemUrl,

	@Schema(description = "폴더 ID", example = "1")
	@NotNull(message = "폴더 ID를 입력하지 않았습니다.")
	Long folderId
) {

	public ItemEnrollServiceRequest toEnrollItemServiceRequest() {
		Hobby hobby = Hobby.from(hobbyValue);

		return new ItemEnrollServiceRequest(hobby, itemUrl);
	}
}
