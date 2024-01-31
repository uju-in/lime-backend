package com.programmers.lime.domains.vote.application.dto.request;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.vote.model.VoteCreateImplRequest;

import lombok.Builder;

@Builder
public record VoteCreateServiceRequest(
	Hobby hobby,
	String content,
	Long item1Id,
	Long item2Id,
	int maximumParticipants
) {
	public VoteCreateImplRequest toImplRequest() {
		return new VoteCreateImplRequest(hobby, content, item1Id, item2Id, maximumParticipants);
	}
}
