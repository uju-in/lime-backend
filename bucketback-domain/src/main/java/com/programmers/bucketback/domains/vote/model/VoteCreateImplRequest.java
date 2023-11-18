package com.programmers.bucketback.domains.vote.model;

import com.programmers.bucketback.Hobby;

public record VoteCreateImplRequest(
	Hobby hobby,
	String content,
	Long item1Id,
	Long item2Id
) {
}
