package com.programmers.bucketback.crawling;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.programmers.bucketback.error.exception.BusinessException;
import com.programmers.bucketback.error.exception.ErrorCode;

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

			return ItemCrawlerResult.builder()
				.itemName(itemName)
				.price(price)
				.imageUrl(imgUrl)
				.url(url)
				.build();
		} catch (Exception e) {
			throw new BusinessException(ErrorCode.CRAWLER_NAVER_BAD_REQUEST);
		}

	}
}
