package com.programmers.bucketback.domains.item.application.dto;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.item.application.crawling.ItemInfo;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateItemServiceRequest(
	@NotNull
	Hobby hobby,

	@NotNull
	String itemName,

	@NotNull
	Integer price,

	@NotNull
	String imageUrl,

	@NotNull
	String url
) {

	public static CreateItemServiceRequest of(
		final EnrollItemServiceRequest request,
		final ItemInfo itemInfo
	) {
		return CreateItemServiceRequest.builder().
			hobby(request.hobby()).
			imageUrl(itemInfo.imageUrl()).
			url(itemInfo.url()).
			price(itemInfo.price()).
			itemName(itemInfo.itemName())
			.build();
	}
}
