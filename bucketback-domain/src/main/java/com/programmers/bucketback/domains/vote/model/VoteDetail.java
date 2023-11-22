package com.programmers.bucketback.domains.vote.model;

import com.programmers.bucketback.domains.item.model.ItemInfo;

import lombok.Builder;

@Builder
public record VoteDetail(
	VoteDetailInfo voteInfo,
	ItemInfo item1Info,
	ItemInfo item2Info,
	boolean isOwner,
	Long selectedItemId
	) {
}
