package com.programmers.bucketback.domains.item.application.dto;

import com.programmers.bucketback.common.model.Hobby;

public record ItemEnrollServiceRequest(
	Hobby hobby,
	String itemUrl
) {
}
