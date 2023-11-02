package com.programmers.bucketback.domains.inventory.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.inventory.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InventoryReader {

	private final InventoryRepository inventoryRepository;

	public boolean isCreated(
		final Hobby hobby,
		final Long memberId
	) {
		return inventoryRepository.existsByHobbyAndMemberId(hobby, memberId);
	}
}
