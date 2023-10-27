package com.programmers.bucketback.domains.item.application.crawling;

import org.jsoup.nodes.Document;

public class CoupangCrawler implements WebCrawler{

    private final String url;

    public CoupangCrawler(final String url) {
        this.url = url;
    }

    @Override
    public ItemInfo extractInfoFromUrl(final String url) {
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

        return new ItemInfo(
            itemName,
            price,
            imgUrl,
            url
        );
    }
}