package com.programmers.bucketback.domains.item.application;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.common.model.ItemIdRegistry;
import com.programmers.bucketback.crawling.ItemCrawlerResult;
import com.programmers.bucketback.crawling.WebCrawler;
import com.programmers.bucketback.crawling.WebSite;
import com.programmers.bucketback.domains.item.application.dto.ItemEnrollServiceRequest;
import com.programmers.bucketback.domains.item.implementation.ItemAppender;
import com.programmers.bucketback.domains.item.model.ItemCrawlerInfo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ItemEnrollService {

	private final ItemAppender itemAppender;
	private final ItemService itemService;
	private final ItemEnrollValidator itemEnrollValidator;

	public Long enrollItem(final ItemEnrollServiceRequest request) {
		// 중복 링크 체크
		itemEnrollValidator.validItemURLNotDuplicated(request.itemUrl());

		// 웹 크롤링을 통한 아이템 정보 가져오기
		WebCrawler webCrawler = WebSite.selectCrawler(request.itemUrl());
		ItemCrawlerResult itemCrawlerResult = webCrawler.extractInfoFromUrl(request.itemUrl());
		ItemCrawlerInfo itemCrawlerInfo = itemCrawlerResultToInfo(itemCrawlerResult);

		// 아이템 등록
		Long enrolledItemId = itemAppender.append(request.hobby(), itemCrawlerInfo);

		// 아이템 담기
		ItemIdRegistry itemIdRegistry = getItemIdRegistry(enrolledItemId);
		itemService.addItem(itemIdRegistry);

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

	private ItemIdRegistry getItemIdRegistry(final Long enrolledItemId) {
		List<Long> ids = List.of(enrolledItemId);

		return new ItemIdRegistry(ids);
	}
}
