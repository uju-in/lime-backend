package com.programmers.bucketback.domains.vote.api.dto.request;

import jakarta.validation.constraints.NotNull;

public record VoteParticipateRequest(
	@NotNull(message = "아이템 ID는 필수 값입니다.")
	Long itemId
) {
}
