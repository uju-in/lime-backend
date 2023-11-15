package com.programmers.bucketback.domains.item.application.dto;

import com.programmers.bucketback.domains.common.Hobby;

public record ItemEnrollServiceRequest(
	Hobby hobby,
	String itemUrl
) {
}
