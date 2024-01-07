package com.programmers.bucketback.domains.feed.api.response;

import java.util.List;

import com.programmers.bucketback.domains.feed.application.dto.response.FeedGetRankingServiceResponse;
import com.programmers.bucketback.redis.feed.model.FeedRankingInfo;

public record FeedGetRankingResponse(
	List<FeedRankingInfo> feedRankingInfos
) {

	public static FeedGetRankingResponse from(final FeedGetRankingServiceResponse serviceResponse) {
		List<FeedRankingInfo> feedRankingInfos = serviceResponse.feedRankingInfos();

		return new FeedGetRankingResponse(feedRankingInfos);
	}
}
