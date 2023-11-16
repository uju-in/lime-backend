package com.programmers.bucketback.domains.item.application.dto;

import com.programmers.bucketback.Hobby;

public record ItemEnrollServiceRequest(
	Hobby hobby,
	String itemUrl
) {
}
