package com.programmers.bucketback.domains.item.application.crawling;

public class ItemInfo {

    private String itemName;
    private Integer price;
    private String imageUrl;
    private String url;

    public ItemInfo(
            final String itemName,
            final Integer price,
            final String imageUrl,
            final String url
    ){
        this.itemName = itemName;
        this.price = price;
        this.imageUrl = imageUrl;
        this.url = url;
    }
}
