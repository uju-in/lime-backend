package com.programmers.bucketback.domains.feed.application.vo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.feed.domain.Feed;

import lombok.Builder;

@Builder
public record FeedInfo(
	Long id,
	Hobby hobby,
	String message,
	String bucketName,
	Integer bucketBudget,
	LocalDateTime createdAt,
	boolean hasAdoptedComment,
	int likeCount,

	@JsonInclude(JsonInclude.Include.NON_NULL)
	Boolean isLiked
) {
	public static FeedInfo of(
		final Feed feed,
		final Boolean isLiked
	) {
		return FeedInfo.builder()
			.id(feed.getId())
			.hobby(feed.getHobby())
			.message(feed.getMessage())
			.bucketName(feed.getName())
			.bucketBudget(feed.getBudget())
			.createdAt(feed.getCreatedAt())
			.hasAdoptedComment(feed.hasAdoptedComment())
			.likeCount(feed.getLikes().size())
			.isLiked(isLiked)
			.build();
	}
}
