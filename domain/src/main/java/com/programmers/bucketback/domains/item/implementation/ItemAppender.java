package com.programmers.bucketback.domains.item.implementation;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.Hobby;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.model.ItemCrawlerInfo;
import com.programmers.bucketback.domains.item.repository.ItemRepository;

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
