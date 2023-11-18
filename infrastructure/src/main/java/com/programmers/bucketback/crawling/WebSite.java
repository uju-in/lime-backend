package com.programmers.bucketback.crawling;

import java.util.function.Function;

public enum WebSite {

	NAVER("naver", NaverCrawler::new),
	DANAWA("danawa", DanawaCrawler::new),
	COUPANG("coupang", CoupangCrawler::new);

	private final String market;
	private final Function<String, WebCrawler> marketFactory;

	WebSite(
		final String market,
		final Function<String, WebCrawler> marketFactory
	) {
		this.market = market;
		this.marketFactory = marketFactory;
	}

	public static WebCrawler selectCrawler(final String url) {
		for (WebSite site : values()) {
			if (url.contains(site.market)) {

				return site.marketFactory.apply(url);
			}
		}
		throw new RuntimeException("지원하지 않는 아이템 URL 입니다."); // 정해진 웹사이트에 해당하는 정보가 아닌 경우
	}
}
