package com.programmers.bucketback.domains.item.application.dto;

import java.util.List;

import com.programmers.bucketback.domains.item.api.dto.response.ItemGetByCursorResponse;
import com.programmers.bucketback.domains.item.application.vo.ItemCursorSummary;

public record ItemGetByCursorServiceResponse(
	String nextCursorId,
	List<ItemCursorSummary> items
) {
	public ItemGetByCursorResponse toItemGetByCursorResponse() {
		return new ItemGetByCursorResponse(
			nextCursorId,
			items
		);
	}
}
