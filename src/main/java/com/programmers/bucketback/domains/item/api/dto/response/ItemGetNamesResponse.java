package com.programmers.bucketback.domains.item.api.dto.response;

import java.util.List;

import com.programmers.bucketback.domains.item.application.dto.GetItemNameResult;

public record ItemGetNamesResponse(
	List<GetItemNameResult> getItemNameResults
) {
}
