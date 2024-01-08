package com.programmers.lime.domains.item.domain;

import java.util.List;
import java.util.stream.LongStream;

import org.springframework.test.util.ReflectionTestUtils;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.bucket.model.ItemImage;
import com.programmers.lime.domains.item.model.ItemCrawlerInfo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemBuilder {

	public static Item build() {
		Item item = aItemBuilder()
			.build();

		setItemId(item, 1L);

		return item;
	}

	public static Item build(final Long id) {

		Item item = aItemBuilder()
			.build();

		setItemId(item, id);

		return item;
	}

	public static List<Item> buildMany() {
		return LongStream.range(1, 4)
			.mapToObj(ItemBuilder::build)
			.toList();
	}

	public static Item.ItemBuilder aItemBuilder() {
		return Item.builder()
			.url("https://www.naver.com")
			.image("https://www.naver.com//image")
			.price(10000)
			.hobby(Hobby.SWIMMING)
			.name("아이템");
	}

	public static Item fromItemCrawlerInfoBuild(
		final Hobby hobby,
		final ItemCrawlerInfo itemCrawlerInfo
	) {
		Item item = Item.builder()
			.hobby(hobby)
			.url(itemCrawlerInfo.url())
			.price(itemCrawlerInfo.price())
			.name(itemCrawlerInfo.itemName())
			.image(itemCrawlerInfo.imageUrl())
			.build();

		setItemId(item, 1L);

		return item;
	}

	public static List<ItemImage> buildItemImages() {
		return LongStream.range(0, 3)
			.mapToObj(i -> new ItemImage(i, "https://www.naver.com"))
			.toList();
	}

	private static void setItemId(
		final Item item,
		final Long id
	) {
		ReflectionTestUtils.setField(
			item,
			"id",
			id
		);
	}

}
