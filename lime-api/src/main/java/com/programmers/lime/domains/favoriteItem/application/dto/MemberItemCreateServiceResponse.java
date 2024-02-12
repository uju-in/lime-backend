package com.programmers.lime.domains.favoriteItem.application.dto;

import java.util.List;

public record MemberItemCreateServiceResponse(
	List<Long> itemIds
) {
}
