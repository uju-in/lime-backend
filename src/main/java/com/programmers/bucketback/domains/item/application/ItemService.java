package com.programmers.bucketback.domains.item.application;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.item.application.dto.AddItemServiceRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final AddItemService addItemService;

	public void addItem(AddItemServiceRequest addItemServiceRequest) {
		Long memberId = MemberUtils.getCurrentMemberId();
		addItemService.addItem(addItemServiceRequest.itemIds(), memberId);
	}
}
