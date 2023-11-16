package com.programmers.bucketback.crawling;

import org.jsoup.nodes.Document;

import com.programmers.bucketback.error.exception.BusinessException;
import com.programmers.bucketback.error.exception.ErrorCode;

public class CoupangCrawler implements WebCrawler {

	private final String url;

	public CoupangCrawler(final String url) {
		this.url = url;
	}

	@Override
	public ItemCrawlerInfo extractInfoFromUrl(final String url) {
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

			return ItemCrawlerInfo.builder()
				.itemName(itemName)
				.price(price)
				.imageUrl(imgUrl)
				.url(url)
				.build();
		} catch (Exception e) {
			throw new BusinessException(ErrorCode.CRAWLER_COUPANG_BAD_REQUEST);
		}
	}
}