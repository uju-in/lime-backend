package com.programmers.bucketback.domains.vote.model;

import com.programmers.bucketback.common.model.Hobby;

import lombok.Builder;

@Builder
public record VoteCreateImplRequest(
	Hobby hobby,
	String content,
	Long item1Id,
	Long item2Id
) {
}
