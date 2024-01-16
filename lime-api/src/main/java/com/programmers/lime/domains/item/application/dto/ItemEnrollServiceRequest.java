package com.programmers.lime.domains.item.application.dto;

import com.programmers.lime.common.model.Hobby;

public record ItemEnrollServiceRequest(
	Hobby hobby,
	String itemUrl
) {
}
