package com.programmers.bucketback.domains.item.application;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.crawling.ItemCrawlerInfo;
import com.programmers.bucketback.crawling.WebCrawler;
import com.programmers.bucketback.crawling.WebSite;
import com.programmers.bucketback.domains.item.application.dto.ItemCreateServiceRequest;
import com.programmers.bucketback.domains.item.application.dto.ItemEnrollServiceRequest;
import com.programmers.bucketback.domains.item.application.dto.MemberItemAddServiceRequest;
import com.programmers.bucketback.domains.item.implementation.ItemAppender;

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
		ItemCrawlerInfo itemInfo = webCrawler.extractInfoFromUrl(request.itemUrl());

		// 아이템 등록
		ItemCreateServiceRequest itemCreateServiceRequest = ItemCreateServiceRequest.of(request.hobby(), itemInfo);
		Long enrolledItemId = itemAppender.append(itemCreateServiceRequest);

		// 아이템 담기
		MemberItemAddServiceRequest memberItemAddServiceRequest = getAddMemberItemServiceRequest(enrolledItemId);
		itemService.addItem(memberItemAddServiceRequest);

		return enrolledItemId;
	}

	private MemberItemAddServiceRequest getAddMemberItemServiceRequest(final Long enrolledItemId) {
		List<Long> ids = List.of(enrolledItemId);

		return new MemberItemAddServiceRequest(ids);
	}
}
