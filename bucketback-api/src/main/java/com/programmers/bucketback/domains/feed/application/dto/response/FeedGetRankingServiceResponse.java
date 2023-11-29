package com.programmers.bucketback.domains.feed.application.dto.response;

import java.util.List;

import com.programmers.bucketback.redis.feed.model.FeedRankingInfo;

public record FeedGetRankingServiceResponse(
	List<FeedRankingInfo> feedRankingInfos
) {
}
