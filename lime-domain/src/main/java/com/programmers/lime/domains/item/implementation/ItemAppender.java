package com.programmers.lime.domains.item.implementation;

import org.springframework.stereotype.Component;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.item.domain.Item;
import com.programmers.lime.domains.item.model.ItemCrawlerInfo;
import com.programmers.lime.domains.item.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ItemAppender {

	private final ItemRepository itemRepository;

	public Long append(
		final Hobby hobby,
		final ItemCrawlerInfo itemInfo
	) {
		Item item = Item.builder().
			hobby(hobby).
			image(itemInfo.imageUrl()).
			url(itemInfo.url()).
			price(itemInfo.price()).
			name(itemInfo.itemName())
			.build();

		Item savedItem = itemRepository.save(item);

		return savedItem.getId();
	}
}
