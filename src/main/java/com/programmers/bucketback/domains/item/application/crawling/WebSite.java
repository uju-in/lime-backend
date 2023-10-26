package com.programmers.bucketback.domains.item.application.crawling;

import com.programmers.bucketback.global.error.exception.EntityNotFoundException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import java.util.function.Function;

public enum WebSite {

    NAVER("naver",NaverCrawler::new),
    DANAWA("danawa",DanawaCrawler::new),
    COUPANG("coupang",CoupangCrawler::new);

    private String market;
    private Function<String,WebCrawler> marketFactory;

    WebSite(String market, Function<String,WebCrawler> marketFactory) {
        this.market = market;
        this.marketFactory = marketFactory;
    }

    public static WebCrawler selectCrawler(String url) {
        for (WebSite site : values()) {
            if (url.contains(site.market)) {

                return site.marketFactory.apply(url);
            }
        }

        throw new EntityNotFoundException(ErrorCode.ITEM_MARKET_NOT_FOUND); // 정해진 웹사이트에 해당하는 정보가 아닌경우
    }
}
