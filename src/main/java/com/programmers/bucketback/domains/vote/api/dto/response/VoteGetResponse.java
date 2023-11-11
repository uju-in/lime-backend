package com.programmers.bucketback.domains.vote.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.programmers.bucketback.domains.item.application.vo.ItemInfo;
import com.programmers.bucketback.domains.vote.application.VoteInfo;
import com.programmers.bucketback.domains.vote.application.dto.response.GetVoteServiceResponse;

import lombok.Builder;

@Builder
public record VoteGetResponse(
	ItemInfo item1Info,
	ItemInfo item2Info,
	VoteInfo voteInfo,
	boolean isOwner,

	@JsonInclude(JsonInclude.Include.NON_NULL)
	Long selectedItemId
) {
	public static VoteGetResponse from(final GetVoteServiceResponse voteServiceResponse) {
		return VoteGetResponse.builder()
			.item1Info(voteServiceResponse.item1Info())
			.item2Info(voteServiceResponse.item2Info())
			.voteInfo(voteServiceResponse.voteInfo())
			.isOwner(voteServiceResponse.isOwner())
			.selectedItemId(voteServiceResponse.selectedItemId())
			.build();
	}
}
