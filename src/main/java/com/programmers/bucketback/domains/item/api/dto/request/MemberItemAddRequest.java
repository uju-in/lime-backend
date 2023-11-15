package com.programmers.bucketback.domains.item.api.dto.request;

import java.util.List;

import com.programmers.bucketback.domains.item.application.dto.MemberItemAddServiceRequest;

import jakarta.validation.constraints.NotNull;

public record MemberItemAddRequest(
	@NotNull(message = "아이템 목록은 필수 값 입니다.")
	List<Long> itemIds
) {
	public MemberItemAddServiceRequest toAddMemberItemServiceRequest() {
		return new MemberItemAddServiceRequest(itemIds);
	}
}
