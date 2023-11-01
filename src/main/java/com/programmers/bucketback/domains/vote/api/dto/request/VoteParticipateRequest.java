package com.programmers.bucketback.domains.vote.api.dto.request;

import jakarta.validation.constraints.NotNull;

public record VoteParticipateRequest(
	@NotNull
	Long itemId
) {
}
