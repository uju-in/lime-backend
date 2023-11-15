package com.programmers.bucketback.domains.item.application.crawling;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

public class NaverCrawler implements WebCrawler {

	private final String url;

	public NaverCrawler(final String url) {
		this.url = url;
	}

	@Override
	public ItemCrawlerInfo extractInfoFromUrl(final String url) {
		Document document = connectWithHeaders(url);

		Elements elements = document.getElementsByClass("_22kNQuEXmb _copyable");
		if (elements.size() == 0) {
			throw new BusinessException(ErrorCode.INVALID_REQUEST);
		}

		String itemName = elements
			.get(0)
			.text();
		Integer price = Integer.parseInt(document.getElementsByClass("_1LY7DqCnwR")
			.last()
			.text()
			.replace(",", ""));
		String imgUrl = "https:" + document.select("img[alt=대표이미지]")
			.first()
			.attr("src");

		return ItemCrawlerInfo.builder()
			.itemName(itemName)
			.price(price)
			.imageUrl(imgUrl)
			.url(url)
			.build();
	}
}
