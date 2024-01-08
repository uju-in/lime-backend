package com.programmers.lime.crawling;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public interface WebCrawler {

	/**
	 * 예외처리 방식 결정하기
	 */
	default Document connectWithHeaders(final String url) {
		try {
			Connection connection = Jsoup.connect(url);
			connection.userAgent(
				"Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36");
			connection.header("scheme", "https");
			connection.header("accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
			connection.header("accept-encoding", "gzip, deflate, br");
			connection.header("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,es;q=0.6");
			connection.header("cache-control", "no-cache");
			connection.header("pragma", "no-cache");
			connection.header("upgrade-insecure-requests", "1");

			return connection.get();
		} catch (IOException e) {
			e.printStackTrace();

			return null;
		}

	}

	ItemCrawlerResult extractInfoFromUrl(final String url);
}
