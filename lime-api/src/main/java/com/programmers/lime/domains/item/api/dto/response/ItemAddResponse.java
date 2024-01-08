package com.programmers.lime.domains.item.api.dto.response;

import java.util.List;

import com.programmers.lime.domains.item.application.dto.ItemAddServiceResponse;

public record ItemAddResponse(List<Long> itemIds) {
	public static ItemAddResponse from(final ItemAddServiceResponse response) {
		return new ItemAddResponse(
			response.itemIds()
		);
	}
}
