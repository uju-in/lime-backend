package com.programmers.bucketback.domains.vote.api.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.programmers.bucketback.domains.vote.application.OptionItem;
import com.programmers.bucketback.domains.vote.application.dto.response.GetVoteServiceResponse;

import lombok.Builder;

@Builder
public record VoteGetResponse(
	OptionItem option1Item,
	OptionItem option2Item,
	String content,
	LocalDateTime createAt,
	boolean isVoting,
	int option1Votes,
	int option2Votes,
	int participants,

	@JsonInclude(JsonInclude.Include.NON_NULL)
	Long selectedItemId
) {
	public static VoteGetResponse from(final GetVoteServiceResponse voteServiceResponse) {
		return VoteGetResponse.builder()
			.option1Item(voteServiceResponse.option1Item())
			.option2Item(voteServiceResponse.option2Item())
			.content(voteServiceResponse.content())
			.createAt(voteServiceResponse.createAt())
			.isVoting(voteServiceResponse.isVoting())
			.option1Votes(voteServiceResponse.option1Votes())
			.option2Votes(voteServiceResponse.option2Votes())
			.participants(voteServiceResponse.participants())
			.selectedItemId(voteServiceResponse.selectedItemId())
			.build();
	}
}
