package com.programmers.bucketback.domains.item.application;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.item.application.dto.AddMemberItemServiceRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final AddMemberItemService addMemberItemService;

	public void addItem(AddMemberItemServiceRequest request) {
		Long memberId = MemberUtils.getCurrentMemberId();
		addMemberItemService.addMemberItems(request.itemIds(), memberId);
	}
}
