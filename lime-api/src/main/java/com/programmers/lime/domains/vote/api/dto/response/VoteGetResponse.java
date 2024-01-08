package com.programmers.lime.domains.vote.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.programmers.lime.domains.item.model.ItemInfo;
import com.programmers.lime.domains.vote.application.dto.response.VoteGetServiceResponse;
import com.programmers.lime.domains.vote.model.VoteDetailInfo;

import lombok.Builder;

@Builder
public record VoteGetResponse(
	ItemInfo item1Info,
	ItemInfo item2Info,
	VoteDetailInfo voteInfo,
	boolean isOwner,

	@JsonInclude(JsonInclude.Include.NON_NULL)
	Long selectedItemId
) {
	public static VoteGetResponse from(final VoteGetServiceResponse voteServiceResponse) {
		return VoteGetResponse.builder()
			.item1Info(voteServiceResponse.item1Info())
			.item2Info(voteServiceResponse.item2Info())
			.voteInfo(voteServiceResponse.voteInfo())
			.isOwner(voteServiceResponse.isOwner())
			.selectedItemId(voteServiceResponse.selectedItemId())
			.build();
	}
}
