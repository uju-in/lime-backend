package com.programmers.lime.domains.vote.application.dto.response;

import com.programmers.lime.domains.item.model.ItemInfo;
import com.programmers.lime.domains.vote.model.VoteDetail;
import com.programmers.lime.domains.vote.model.VoteDetailInfo;

import lombok.Builder;

@Builder
public record VoteGetServiceResponse(
	ItemInfo item1Info,
	ItemInfo item2Info,
	VoteDetailInfo voteInfo,
	boolean isOwner,
	Long selectedItemId
) {
	public static VoteGetServiceResponse from(final VoteDetail detail) {
		return VoteGetServiceResponse.builder()
			.item1Info(detail.item1Info())
			.item2Info(detail.item2Info())
			.voteInfo(detail.voteInfo())
			.isOwner(detail.isOwner())
			.selectedItemId(detail.selectedItemId())
			.build();
	}
}
