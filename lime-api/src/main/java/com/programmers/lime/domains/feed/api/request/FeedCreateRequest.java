package com.programmers.lime.domains.feed.api.request;

import java.util.List;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.common.model.ItemIdRegistry;
import com.programmers.lime.domains.feed.model.FeedCreateServiceRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record FeedCreateRequest(

	@Schema(description = "취미", example = "농구")
	@NotNull(message = "취미를 선택해주세요")
	String hobby,

	@Schema(description = "선호 아이템 아이디", example = "[1,2,3]")
	@NotNull(message = "아이템 아이디를 입력하세요")
	List<Long> itemIds,

	@Schema(description = "피드 내용", example = "님들 내 버킷템 어떰?")
	@NotNull(message = "피드 내용을 입력하세요")
	String content,

	@Schema(description = "예산", example = "100000")
	@Min(value = 1, message = "최소 에산은 0 초과입니다.")
	Integer budget

) {
	public FeedCreateServiceRequest toServiceRequest() {
		return new FeedCreateServiceRequest(Hobby.from(hobby), new ItemIdRegistry(itemIds), content, budget);
	}
}
