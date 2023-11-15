package com.programmers.bucketback.domains.vote.application.dto.response;

import com.programmers.bucketback.domains.item.application.vo.ItemInfo;
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
