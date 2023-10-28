package com.programmers.bucketback.domains.item.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.item.application.dto.CreateItemServiceRequest;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CreateItemService {

	private final ItemRepository itemRepository;

	@Transactional
	public void createItem(final CreateItemServiceRequest request) {
		Item item = Item.builder().
			hobby(request.hobby()).
			image(request.imageUrl()).
			url(request.url()).
			price(request.price()).
			name(request.itemName())
			.build();

		itemRepository.save(item);
	}
}
