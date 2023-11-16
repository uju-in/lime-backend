package com.programmers.bucketback.domains.item.application.dto;

import java.util.List;

public record ItemAddServiceResponse(
	List<Long> itemIds
) {
}
