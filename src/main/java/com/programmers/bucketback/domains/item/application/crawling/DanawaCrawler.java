package com.programmers.bucketback.domains.item.application.crawling;

import org.jsoup.nodes.Document;

public class DanawaCrawler implements WebCrawler {

    private final String url;

    public DanawaCrawler(final String url) {
        this.url = url;
    }

    @Override
    public ItemInfo extractInfoFromUrl(final String url) {
        Document document = connectWithHeaders(url);

        String itemName = document.select("span.title")
                .get(0)
                .text();
        Integer price = Integer.parseInt(document.select("em.prc_c")
                .last()
                .text()
                .replace(",",""));
        String imgUrl = "https:" + document.getElementById("baseImage")
                .attr("src");

        return new ItemInfo(itemName, price, imgUrl, url);
    }

}
