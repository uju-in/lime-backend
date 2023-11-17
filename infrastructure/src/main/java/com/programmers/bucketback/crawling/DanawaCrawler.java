package com.programmers.bucketback.crawling;

import org.jsoup.nodes.Document;

import com.programmers.bucketback.error.exception.BusinessException;
import com.programmers.bucketback.error.exception.ErrorCode;

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
			throw new BusinessException(ErrorCode.CRAWLER_DANAWA_BAD_REQUEST);
		}
	}

}
