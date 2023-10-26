package com.programmers.bucketback.domains.item.application.crawling;

import org.jsoup.nodes.Document;

public class NaverCrawler implements WebCrawler{

    private final String url;

    public NaverCrawler(String url) {
        this.url = url;
    }

    @Override
    public ItemInfo extractInfoFromUrl(final String url) {
        Document document = connectWithHeaders(url);

        String itemName = document.getElementsByClass("_22kNQuEXmb _copyable")
                .get(0)
                .text();
        Integer price = Integer.parseInt(document.getElementsByClass("_1LY7DqCnwR")
                .last()
                .text()
                .replace(",", ""));
        String imgUrl = "https:" + document.select("img[alt=대표이미지]")
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
