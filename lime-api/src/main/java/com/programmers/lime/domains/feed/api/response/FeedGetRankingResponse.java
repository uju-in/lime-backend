package com.programmers.lime.domains.feed.api.response;

import java.util.List;

import com.programmers.lime.domains.feed.application.dto.response.FeedGetRankingServiceResponse;
import com.programmers.lime.redis.feed.model.FeedRankingInfo;

public record FeedGetRankingResponse(
	List<FeedRankingInfo> feedRankingInfos
) {

	public static FeedGetRankingResponse from(final FeedGetRankingServiceResponse serviceResponse) {
		List<FeedRankingInfo> feedRankingInfos = serviceResponse.feedRankingInfos();

		return new FeedGetRankingResponse(feedRankingInfos);
	}
}
