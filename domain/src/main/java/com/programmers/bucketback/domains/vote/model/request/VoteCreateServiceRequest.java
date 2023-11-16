package com.programmers.bucketback.domains.vote.model.request;

import com.programmers.bucketback.Hobby;

import lombok.Builder;

@Builder
public record VoteCreateServiceRequest(
	Hobby hobby,
	String content,
	Long item1Id,
	Long item2Id
) {
}
