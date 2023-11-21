package com.programmers.bucketback.domains.vote.application.dto.response;

import com.programmers.bucketback.domains.item.model.ItemInfo;
import com.programmers.bucketback.domains.vote.model.VoteInfo;
import com.programmers.bucketback.domains.vote.model.VoteSummary;

import lombok.Builder;

@Builder
public record VoteGetServiceResponse(
	ItemInfo item1Info,
	ItemInfo item2Info,
	VoteInfo voteInfo,
	boolean isOwner,
	Long selectedItemId
) {
	public static VoteGetServiceResponse from(final VoteSummary summary) {
		return VoteGetServiceResponse.builder()
			.item1Info(summary.item1Info())
			.item2Info(summary.item2Info())
			.voteInfo(summary.voteInfo())
			.isOwner(summary.isOwner())
			.selectedItemId(summary.selectedItemId())
			.build();
	}
}
