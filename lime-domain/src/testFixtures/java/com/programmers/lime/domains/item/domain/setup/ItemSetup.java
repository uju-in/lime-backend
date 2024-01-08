package com.programmers.lime.domains.item.domain.setup;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.item.domain.Item;
import com.programmers.lime.domains.item.domain.ItemBuilder;
import com.programmers.lime.domains.item.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ItemSetup {

	private final ItemRepository itemRepository;

	public Item saveOne(final Long itemId) {
		Item item = ItemBuilder.build(itemId);

		return itemRepository.save(item);
	}
}
