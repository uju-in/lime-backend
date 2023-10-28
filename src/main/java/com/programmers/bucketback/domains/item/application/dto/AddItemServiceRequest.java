package com.programmers.bucketback.domains.item.application.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record AddItemServiceRequest(
	@NotNull
	List<Long> itemIds
) {
	public AddItemServiceRequest(@NotNull final List<Long> itemIds) {
		this.itemIds = itemIds;
	}
}
