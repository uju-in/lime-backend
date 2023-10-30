package com.programmers.bucketback.domains.vote.application.dto.request;

import com.programmers.bucketback.domains.common.Hobby;

import lombok.Builder;

@Builder
public record CreateVoteServiceRequest(
	Hobby hobby,
	String content,
	Long option1ItemId,
	Long option2ItemId
) {
}
