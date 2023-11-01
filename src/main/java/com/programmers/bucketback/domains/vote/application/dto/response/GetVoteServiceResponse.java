package com.programmers.bucketback.domains.vote.application.dto.response;

import java.time.LocalDateTime;

import com.programmers.bucketback.domains.vote.application.OptionItem;

import lombok.Builder;

@Builder
public record GetVoteServiceResponse(
	OptionItem option1Item,
	OptionItem option2Item,
	String content,
	LocalDateTime createAt,
	boolean isVoting,
	int option1Votes,
	int option2Votes,
	int participants,
	Long selectedItemId
) {
}
