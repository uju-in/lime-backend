package com.programmers.bucketback.domains.item.application;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.item.application.dto.GetItemNameResult;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ItemFinder {

	private final ItemRepository itemRepository;

	public List<GetItemNameResult> getItemNamesByKeyword(final String name) {
		List<Item> items = itemRepository.findItemsByNameContains(name);

		return items.stream()
			.map(GetItemNameResult::from)
			.toList();
	}
}
