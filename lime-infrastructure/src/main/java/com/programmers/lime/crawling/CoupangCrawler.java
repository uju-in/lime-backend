package com.programmers.lime.crawling;

import org.jsoup.nodes.Document;

public class CoupangCrawler implements WebCrawler {

	private final String url;

	public CoupangCrawler(final String url) {
		this.url = url;
	}

	@Override
	public ItemCrawlerResult extractInfoFromUrl(final String url) {
		try {
			Document document = connectWithHeaders(url);

			String itemName = document.getElementsByClass("prod-buy-header__title")
				.get(0)
				.text();
			Integer price = Integer.parseInt(document.getElementsByClass("total-price")
				.last()
				.text()
				.replace(",", "")
				.replace("원", ""));
			String imgUrl = "https:" + document.getElementsByClass("prod-image__detail")
				.first()
				.attr("src");

			return ItemCrawlerResult.builder()
				.itemName(itemName)
				.price(price)
				.imageUrl(imgUrl)
				.url(url)
				.build();
		} catch (Exception e) {
			throw new RuntimeException("쿠팡 크롤러에서 파싱할 수 없는 URL 입니다.");
		}
	}
}