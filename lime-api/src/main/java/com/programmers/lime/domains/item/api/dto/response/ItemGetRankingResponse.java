package com.programmers.lime.domains.item.api.dto.response;

import java.util.List;

import com.programmers.lime.redis.dto.ItemRankingServiceResponse;

public record ItemGetRankingResponse(
	List<ItemRankingServiceResponse> itemRanking
) {
	public static ItemGetRankingResponse from(final List<ItemRankingServiceResponse> serviceResponses) {
		return new ItemGetRankingResponse(serviceResponses);
	}
}
