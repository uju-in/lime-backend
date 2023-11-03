package com.programmers.bucketback.domains.item.application;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.item.application.vo.ItemInfo;
import com.programmers.bucketback.domains.item.domain.Item;

@Component
public class ItemMapper {

	public ItemInfo getItemInfo(final Item item) {
		return ItemInfo.builder()
			.id(item.getId())
			.name(item.getName())
			.price(item.getPrice())
			.image(item.getImage())
			.build();
	}
}
