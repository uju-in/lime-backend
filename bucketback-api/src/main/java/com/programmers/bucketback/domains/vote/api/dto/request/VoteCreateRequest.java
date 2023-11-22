package com.programmers.bucketback.domains.vote.api.dto.request;

import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.domains.vote.application.dto.request.VoteCreateServiceRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record VoteCreateRequest(
	@Schema(description = "취미", example = "농구")
	@NotNull(message = "취미는 필수 값입니다.")
	String hobby,

	@Schema(description = "투표 내용", example = "어떤 농구화가 더 이쁜가?")
	@Size(max = 1000, message = "투표 내용은 최대 1000자 입니다.")
	@NotNull(message = "투표 내용은 필수 값입니다.")
	String content,

	@Schema(description = "아이템1 ID", example = "1")
	@NotNull(message = "아이템1 ID는 필수 값입니다.")
	Long item1Id,

	@Schema(description = "아이템2 ID", example = "2")
	@NotNull(message = "아이템2 ID는 필수 값입니다.")
	Long item2Id
) {
	public VoteCreateServiceRequest toCreateVoteServiceRequest() {
		return VoteCreateServiceRequest.builder()
			.hobby(Hobby.fromHobbyValue(hobby))
			.content(content)
			.item1Id(item1Id)
			.item2Id(item2Id)
			.build();
	}
}
