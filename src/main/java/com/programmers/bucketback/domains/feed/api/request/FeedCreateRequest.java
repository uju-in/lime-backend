package com.programmers.bucketback.domains.feed.api.request;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.global.annotation.Enum;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record FeedCreateRequest(

	@Schema(description = "취미", example = "농구")
	@NotNull(message = "취미를 입력하세요")
	@Enum
	Hobby hobby,

	@Schema(description = "버킷 아이디", example = "1")
	@NotNull(message = "버킷 아이디를 입력하세요")
	Long bucketId,

	@Schema(description = "피드 내용", example = "님들 내 버킷템 어떰?")
	@NotNull(message = "피드 내용을 입력하세요")
	String message

) {
	public FeedContent toContent(){
		return new FeedContent(hobby,bucketId,message);
	}
}
