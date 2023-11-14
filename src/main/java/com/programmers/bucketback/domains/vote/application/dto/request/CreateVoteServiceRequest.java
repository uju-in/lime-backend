package com.programmers.bucketback.domains.vote.application.dto.request;

import com.programmers.bucketback.domains.common.Hobby;

import lombok.Builder;

@Builder
public record CreateVoteServiceRequest(
	Hobby hobby,
	String content,
	Long item1Id,
	Long item2Id
) {
}
