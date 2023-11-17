package com.programmers.bucketback.domains.vote.application.dto.response;

import com.programmers.bucketback.domains.item.model.ItemInfo;
import com.programmers.bucketback.domains.vote.model.VoteCursorSummary;
import com.programmers.bucketback.domains.vote.model.response.VoteInfo;

import lombok.Builder;

@Builder
public record VoteGetServiceResponse(
	ItemInfo item1Info,
	ItemInfo item2Info,
	VoteInfo voteInfo,
	boolean isOwner,
	Long selectedItemId
) {
	public static VoteGetServiceResponse from(final VoteCursorSummary summary) {
		return VoteGetServiceResponse.builder()
			.item1Info(summary.item1Info())
			.item2Info(summary.item2Info())
			.voteInfo(summary.voteInfo())
			.isOwner(summary.isOwner())
			.selectedItemId(builder().selectedItemId)
			.build();
	}
}
