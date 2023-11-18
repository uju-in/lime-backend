package com.programmers.bucketback.domains.vote.application.dto.request;

import com.programmers.bucketback.Hobby;
import com.programmers.bucketback.domains.vote.model.VoteCreateImplRequest;

import lombok.Builder;

@Builder
public record VoteCreateServiceRequest(
	Hobby hobby,
	String content,
	Long item1Id,
	Long item2Id
) {
	public VoteCreateImplRequest toImplRequest() {
		return new VoteCreateImplRequest(hobby, content, item1Id, item2Id);
	}
}
