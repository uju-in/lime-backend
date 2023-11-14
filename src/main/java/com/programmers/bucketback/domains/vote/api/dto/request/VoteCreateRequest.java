package com.programmers.bucketback.domains.vote.api.dto.request;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.vote.application.dto.request.VoteCreateServiceRequest;
import jakarta.validation.constraints.NotNull;

public record VoteCreateRequest(
	@NotNull
	Hobby hobby,

	@NotNull
	String content,

	@NotNull
	Long item1Id,

	@NotNull
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
