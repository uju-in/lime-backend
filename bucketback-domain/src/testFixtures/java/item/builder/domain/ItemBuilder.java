package item.builder.domain;

import org.springframework.test.util.ReflectionTestUtils;

import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.model.ItemCrawlerInfo;

public class ItemBuilder {

	public static Item build() {
		Item item = itemWithURLBuilder()
			.price(10000)
			.name("아이템")
			.build();

		setItemId(item, 1L);

		return item;
	}

	public static Item.ItemBuilder itemWithURLBuilder() {
		return Item.builder()
			.url("https://www.naver.com")
			.image("https://www.naver.com//image");
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
