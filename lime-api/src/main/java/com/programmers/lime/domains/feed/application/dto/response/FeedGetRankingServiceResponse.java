package com.programmers.lime.domains.feed.application.dto.response;

import java.util.List;

import com.programmers.lime.redis.feed.model.FeedRankingInfo;

public record FeedGetRankingServiceResponse(
	List<FeedRankingInfo> feedRankingInfos
) {
}
