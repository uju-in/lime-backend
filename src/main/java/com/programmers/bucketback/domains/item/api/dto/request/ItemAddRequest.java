package com.programmers.bucketback.domains.item.api.dto.request;

import java.util.List;

import com.programmers.bucketback.domains.item.application.dto.AddItemServiceRequest;

import jakarta.validation.constraints.NotNull;

public record ItemAddRequest(
	@NotNull
	List<Long> itemIds
) {
	public AddItemServiceRequest toAddItemServiceRequest() {
		return new AddItemServiceRequest(itemIds);
	}
}
