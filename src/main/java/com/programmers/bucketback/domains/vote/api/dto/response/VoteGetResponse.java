package com.programmers.bucketback.domains.vote.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.programmers.bucketback.domains.vote.application.OptionItem;
import com.programmers.bucketback.domains.vote.application.VoteInfo;
import com.programmers.bucketback.domains.vote.application.dto.response.GetVoteServiceResponse;

import lombok.Builder;

@Builder
public record VoteGetResponse(
	OptionItem option1Item,
	OptionItem option2Item,
	VoteInfo voteInfo,
	boolean isOwner,

	@JsonInclude(JsonInclude.Include.NON_NULL)
	Long selectedItemId
) {
	public static VoteGetResponse from(final GetVoteServiceResponse voteServiceResponse) {
		return VoteGetResponse.builder()
			.option1Item(voteServiceResponse.option1Item())
			.option2Item(voteServiceResponse.option2Item())
			.voteInfo(voteServiceResponse.voteInfo())
			.isOwner(voteServiceResponse.isOwner())
			.selectedItemId(voteServiceResponse.selectedItemId())
			.build();
	}
}
