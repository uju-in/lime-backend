package com.programmers.lime.domains.feed.api.request;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.feed.application.dto.request.FeedGetCursorServiceRequest;
import com.programmers.lime.domains.feed.model.FeedSortCondition;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record FeedCursorGetRequest(

	@Schema(description = "취미 이름", example = "농구")
	String hobbyName,

	@Schema(description = "닉네임", example = "test0")
	String nickname,

	@Schema(description = "정렬 조건", example = "POPULARITY")
	String sortCondition

) {
	public FeedGetCursorServiceRequest toServiceRequest() {

		FeedSortCondition feedSortCondition = FeedSortCondition.from(this.sortCondition);
		Hobby hobby = Hobby.from(hobbyName);

		return FeedGetCursorServiceRequest.builder()
			.nickname(nickname)
			.sortCondition(feedSortCondition)
			.hobby(hobby)
			.build();
	}
}