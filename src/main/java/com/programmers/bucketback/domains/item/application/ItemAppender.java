package com.programmers.bucketback.domains.item.application;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.item.application.dto.ItemCreateServiceRequest;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemAppender {

	private final ItemRepository itemRepository;

	public Long append(final ItemCreateServiceRequest request) {
		Item item = Item.builder().
			hobby(request.hobby()).
			image(request.imageUrl()).
			url(request.url()).
			price(request.price()).
			name(request.itemName())
			.build();

		Item savedItem = itemRepository.save(item);
		return savedItem.getId();
	}
}
