package com.programmers.lime.domains.item.application;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.lime.common.model.ItemIdRegistry;
import com.programmers.lime.crawling.ItemCrawlerResult;
import com.programmers.lime.crawling.WebCrawler;
import com.programmers.lime.crawling.WebSite;
import com.programmers.lime.domains.item.application.dto.ItemEnrollServiceRequest;
import com.programmers.lime.domains.item.implementation.ItemAppender;
import com.programmers.lime.domains.item.model.ItemCrawlerInfo;
import com.programmers.lime.redis.implement.ItemRanking;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ItemEnrollService {

	private final ItemAppender itemAppender;

	private final ItemEnrollValidator itemEnrollValidator;

	private final ItemRanking itemRanking;

	public Long enrollItem(final ItemEnrollServiceRequest request) {
		// 중복 링크 체크
		itemEnrollValidator.validItemURLNotDuplicated(request.itemUrl());

		// 웹 크롤링을 통한 아이템 정보 가져오기
		WebCrawler webCrawler = WebSite.selectCrawler(request.itemUrl());
		ItemCrawlerResult itemCrawlerResult = webCrawler.extractInfoFromUrl(request.itemUrl());
		ItemCrawlerInfo itemCrawlerInfo = itemCrawlerResultToInfo(itemCrawlerResult);

		// 아이템 등록
		Long enrolledItemId = itemAppender.append(request.hobby(), request.folderId(), itemCrawlerInfo);

		// 아이텥 랭킹 등록
		itemRanking.addRanking(itemCrawlerInfo.itemName());

		return enrolledItemId;
	}

	private ItemCrawlerInfo itemCrawlerResultToInfo(final ItemCrawlerResult itemCrawlerResult) {
		return ItemCrawlerInfo.builder()
			.itemName(itemCrawlerResult.itemName())
			.price(itemCrawlerResult.price())
			.itemName(itemCrawlerResult.itemName())
			.imageUrl(itemCrawlerResult.imageUrl())
			.url(itemCrawlerResult.url())
			.build();
	}
}
