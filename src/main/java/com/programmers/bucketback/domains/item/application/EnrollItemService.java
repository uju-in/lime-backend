package com.programmers.bucketback.domains.item.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.item.application.crawling.ItemInfo;
import com.programmers.bucketback.domains.item.application.crawling.WebCrawler;
import com.programmers.bucketback.domains.item.application.crawling.WebSite;
import com.programmers.bucketback.domains.item.application.dto.ItemCreateServiceRequest;
import com.programmers.bucketback.domains.item.application.dto.ItemEnrollServiceRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnrollItemService {

	private final CreateItemService createItemService;

	@Transactional
	public void enrollItem(final ItemEnrollServiceRequest request) {
		WebCrawler webCrawler = WebSite.selectCrawler(request.itemUrl());
		ItemInfo itemInfo = webCrawler.extractInfoFromUrl(request.itemUrl());

		ItemCreateServiceRequest itemCreateServiceRequest = ItemCreateServiceRequest.of(request, itemInfo);
		createItemService.createItem(itemCreateServiceRequest);
	}
}
