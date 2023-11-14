package com.programmers.bucketback.domains.vote.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record VoteParticipateRequest(
	@Schema(description = "아이템 ID", example = "1")
	@NotNull(message = "아이템 ID는 필수 값입니다.")
	Long itemId
) {
}
