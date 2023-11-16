package com.programmers.bucketback.domains.item.application.dto;

import java.util.List;

public record MemberItemAddServiceRequest(
	List<Long> itemIds
) {
}
