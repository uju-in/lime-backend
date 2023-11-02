package com.programmers.bucketback.domains.vote.application.dto.response;

import com.programmers.bucketback.domains.vote.application.OptionItem;
import com.programmers.bucketback.domains.vote.application.VoteInfo;

import lombok.Builder;

@Builder
public record GetVoteServiceResponse(
	OptionItem option1Item,
	OptionItem option2Item,
	VoteInfo voteInfo,
	boolean isOwner,
	Long selectedItemId
) {
}
