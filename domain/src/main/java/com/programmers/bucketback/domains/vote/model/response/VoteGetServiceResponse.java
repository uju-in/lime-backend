package com.programmers.bucketback.domains.vote.model.response;

import com.programmers.bucketback.domains.item.model.ItemInfo;
import lombok.Builder;

@Builder
public record VoteGetServiceResponse(
	ItemInfo item1Info,
	ItemInfo item2Info,
	VoteInfo voteInfo,
	boolean isOwner,
	Long selectedItemId
) {
}
