package com.programmers.bucketback.domains.vote.api.dto.request;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.vote.application.dto.request.CreateVoteServiceRequest;

import jakarta.validation.constraints.NotNull;

public record VoteCreateRequest(
	@NotNull
	Hobby hobby,

	@NotNull
	String content,

	@NotNull
	Long option1ItemId,

	@NotNull
	Long option2ItemId
) {
	public CreateVoteServiceRequest toCreateVoteServiceRequest() {
		return CreateVoteServiceRequest.builder()
			.hobby(hobby)
			.content(content)
			.option1ItemId(option1ItemId)
			.option2ItemId(option2ItemId)
			.build();
	}
}
