package com.programmers.bucketback.domains.vote.api.dto.request;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.vote.application.dto.request.VoteCreateServiceRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record VoteCreateRequest(
	@NotNull(message = "취미는 필수 값입니다.")
	Hobby hobby,

	@Size(max = 1000, message = "투표 내용은 최대 1000자 입니다.")
	@NotNull(message = "투표 내용은 필수 값입니다.")
	String content,

	@NotNull(message = "아이템1 ID는 필수 값입니다.")
	Long item1Id,

	@NotNull(message = "아이템2 ID는 필수 값입니다.")
	Long item2Id
) {
	public VoteCreateServiceRequest toCreateVoteServiceRequest() {
		return VoteCreateServiceRequest.builder()
			.hobby(hobby)
			.content(content)
			.item1Id(item1Id)
			.item2Id(item2Id)
			.build();
	}
}
