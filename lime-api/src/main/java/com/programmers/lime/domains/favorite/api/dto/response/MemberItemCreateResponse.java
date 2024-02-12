package com.programmers.lime.domains.favorite.api.dto.response;

import java.util.List;

import com.programmers.lime.domains.favorite.application.dto.MemberItemCreateServiceResponse;

public record MemberItemCreateResponse(List<Long> itemIds) {
	public static MemberItemCreateResponse from(final MemberItemCreateServiceResponse response) {
		return new MemberItemCreateResponse(
			response.itemIds()
		);
	}
}
