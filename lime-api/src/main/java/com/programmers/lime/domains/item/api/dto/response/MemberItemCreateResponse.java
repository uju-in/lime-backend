package com.programmers.lime.domains.item.api.dto.response;

import java.util.List;

import com.programmers.lime.domains.item.application.dto.MemberItemCreateServiceResponse;

public record MemberItemCreateResponse(List<Long> itemIds) {
	public static MemberItemCreateResponse from(final MemberItemCreateServiceResponse response) {
		return new MemberItemCreateResponse(
			response.itemIds()
		);
	}
}
