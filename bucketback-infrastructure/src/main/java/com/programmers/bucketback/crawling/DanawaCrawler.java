package com.programmers.bucketback.crawling;

import org.jsoup.nodes.Document;

public class DanawaCrawler implements WebCrawler {

	private final String url;

	public DanawaCrawler(final String url) {
		this.url = url;
	}

	@Override
	public ItemCrawlerResult extractInfoFromUrl(final String url) {
		try {
			Document document = connectWithHeaders(url);

			String itemName = document.select("span.title")
				.get(0)
				.text();
			Integer price = Integer.parseInt(document.select("em.prc_c")
				.last()
				.text()
				.replace(",", ""));
			String imgUrl = "https:" + document.getElementById("baseImage")
				.attr("src");

			return ItemCrawlerResult.builder()
				.itemName(itemName)
				.price(price)
				.imageUrl(imgUrl)
				.url(url)
				.build();
		} catch (Exception e) {
			throw new RuntimeException("다나와 크롤러에서 파싱할 수 없는 URL 입니다.");
		}
	}

}
