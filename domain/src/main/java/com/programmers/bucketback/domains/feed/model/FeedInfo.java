package com.programmers.bucketback.domains.feed.model;

import java.time.LocalDateTime;

import com.programmers.bucketback.Hobby;
import com.programmers.bucketback.domains.feed.domain.Feed;
import com.programmers.bucketback.domains.feed.domain.FeedContent;

import lombok.Builder;

@Builder
public record FeedInfo(
	Long id,
	Hobby hobby,
	FeedContent content,
	String bucketName,
	Integer bucketBudget,
	LocalDateTime createdAt,
	boolean hasAdoptedComment,
	int likeCount,

	// @JsonInclude(JsonInclude.Include.NON_NULL)
	Boolean isLiked
) {
	public static FeedInfo of(
		final Feed feed,
		final Boolean isLiked
	) {
		return FeedInfo.builder()
			.id(feed.getId())
			.hobby(feed.getHobby())
			.content(feed.getContent())
			.bucketName(feed.getName())
			.bucketBudget(feed.getBudget())
			.createdAt(feed.getCreatedAt())
			.hasAdoptedComment(feed.hasAdoptedComment())
			.likeCount(feed.getLikes().size())
			.isLiked(isLiked)
			.build();
	}
}
