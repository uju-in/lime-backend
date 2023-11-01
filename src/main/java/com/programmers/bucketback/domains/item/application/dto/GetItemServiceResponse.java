package com.programmers.bucketback.domains.item.application.dto;

import com.programmers.bucketback.domains.item.api.dto.response.ItemGetResponse;

import lombok.Builder;

@Builder
public record GetItemServiceResponse(
	Long itemId,
	String itemImage,
	String itemName,
	Integer itemPrice,
	String itemUrl,
	Double itemAvgRate,
	boolean itemIsMine
) {
	public ItemGetResponse toItemGetResponse() {
		return ItemGetResponse.builder()
			.itemId(itemId)
			.itemIsMine(itemIsMine)
			.itemUrl(itemUrl)
			.itemAvgRate(itemAvgRate)
			.itemPrice(itemPrice)
			.itemName(itemName)
			.itemImage(itemImage)
			.build();
	}
}
