package com.programmers.bucketback.domains.item.application.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record AddMemberItemServiceRequest(
	@NotNull
	List<Long> itemIds
) {
	public AddMemberItemServiceRequest(@NotNull final List<Long> itemIds) {
		this.itemIds = itemIds;
	}
}
