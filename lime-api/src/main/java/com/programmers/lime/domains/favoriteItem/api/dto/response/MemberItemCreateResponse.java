package com.programmers.lime.domains.favoriteItem.api.dto.response;

import java.util.List;

import com.programmers.lime.domains.favoriteItem.application.dto.MemberItemCreateServiceResponse;

public record MemberItemCreateResponse(List<Long> itemIds) {
	public static MemberItemCreateResponse from(final MemberItemCreateServiceResponse response) {
		return new MemberItemCreateResponse(
			response.itemIds()
		);
	}
}
