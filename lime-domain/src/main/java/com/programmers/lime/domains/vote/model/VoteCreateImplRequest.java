package com.programmers.lime.domains.vote.model;

import com.programmers.lime.common.model.Hobby;

import lombok.Builder;

@Builder
public record VoteCreateImplRequest(
	Hobby hobby,
	String content,
	Long item1Id,
	Long item2Id,
	Integer maximumParticipants
) {
}
