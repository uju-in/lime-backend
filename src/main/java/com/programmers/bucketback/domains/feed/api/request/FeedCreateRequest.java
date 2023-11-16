package com.programmers.bucketback.domains.feed.api.request;

import com.programmers.bucketback.domains.feed.application.vo.FeedCreateServiceRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record FeedCreateRequest(

	@Schema(description = "버킷 아이디", example = "1")
	@NotNull(message = "버킷 아이디를 입력하세요")
	Long bucketId,

	@Schema(description = "피드 내용", example = "님들 내 버킷템 어떰?")
	@NotNull(message = "피드 내용을 입력하세요")
	String content

) {
	public FeedCreateServiceRequest toServiceRequest() {
		return new FeedCreateServiceRequest(bucketId, content);
	}
}
