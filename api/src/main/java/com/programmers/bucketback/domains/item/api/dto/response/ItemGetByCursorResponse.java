package com.programmers.bucketback.domains.item.api.dto.response;

import java.util.List;

import com.programmers.bucketback.domains.item.application.dto.ItemGetByCursorServiceResponse;
import com.programmers.bucketback.domains.item.model.ItemCursorSummary;

public record ItemGetByCursorResponse(
	String nextCursorId,
	List<ItemCursorSummary> items
) {
	public static ItemGetByCursorResponse from(final ItemGetByCursorServiceResponse response) {
		return new ItemGetByCursorResponse(
			response.nextCursorId(),
			response.items()
		);
	}
}
