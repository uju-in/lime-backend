package com.programmers.bucketback.domains.item.api.dto.response;

import java.util.List;

import com.programmers.bucketback.redis.dto.ItemRankingServiceResponse;

public record ItemGetRankingResponse(
	List<ItemRankingServiceResponse> itemRanking
) {
	public static ItemGetRankingResponse from(final List<ItemRankingServiceResponse> serviceResponses) {
		return new ItemGetRankingResponse(serviceResponses);
	}
}
