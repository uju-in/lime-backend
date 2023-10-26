package com.programmers.bucketback.domains.item.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

/**
 * 아이템 등록시 적용하면 되는 로직
 */
//    WebCrawler webCrawler = WebSite.selectCrawler(crawlingURL);
//
//        if (webCrawler != null){
//        ItemInfo itemInfo = webCrawler.extractInfoFromUrl(crawlingURL);
//        System.out.println(itemInfo.toString());
//    }
//        else {
//        System.out.println("error");
//    }

}
