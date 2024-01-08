package com.programmers.lime.crawling;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class NaverCrawler implements WebCrawler {

	private final String url;

	public NaverCrawler(final String url) {
		this.url = url;
	}

	@Override
	public ItemCrawlerResult extractInfoFromUrl(final String url) {
		try {
			Document document = connectWithHeaders(url);

			Elements elements = document.getElementsByClass("_22kNQuEXmb _copyable");
			if (elements.size() == 0) {
				throw new RuntimeException("유효하지 않은 요청입니다.");
			}

			String itemName = elements
				.get(0)
				.text();
			Integer price = Integer.parseInt(document.getElementsByClass("_1LY7DqCnwR")
				.last()
				.text()
				.replace(",", ""));
			String imgUrl = document.select("img[alt=대표이미지]")
				.first()
				.attr("src");

			return ItemCrawlerResult.builder()
				.itemName(itemName)
				.price(price)
				.imageUrl(imgUrl)
				.url(url)
				.build();
		} catch (Exception e) {
			throw new RuntimeException("네이버 크롤러에서 파싱할 수 없는 URL 입니다.");
		}

	}
}
