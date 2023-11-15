package com.programmers.bucketback.domains.item.api.dto.response;

import com.programmers.bucketback.domains.item.application.dto.ItemEnrollServiceResponse;

public record ItemEnrollResponse(Long itemId) {
	public static ItemEnrollResponse from(final ItemEnrollServiceResponse response) {
		return new ItemEnrollResponse(
			response.itemId()
		);
	}
}
