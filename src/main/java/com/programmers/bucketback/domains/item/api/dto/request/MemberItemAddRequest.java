package com.programmers.bucketback.domains.item.api.dto.request;

import java.util.List;

import com.programmers.bucketback.domains.item.application.dto.MemberItemAddServiceRequest;

import jakarta.validation.constraints.NotNull;

public record MemberItemAddRequest(
	@NotNull
	List<Long> itemIds
) {
	public MemberItemAddServiceRequest toAddMemberItemServiceRequest() {
		return new MemberItemAddServiceRequest(itemIds);
	}
}
