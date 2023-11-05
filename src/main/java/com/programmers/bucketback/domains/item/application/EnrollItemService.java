package com.programmers.bucketback.domains.item.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.item.application.crawling.ItemInfo;
import com.programmers.bucketback.domains.item.application.crawling.WebCrawler;
import com.programmers.bucketback.domains.item.application.crawling.WebSite;
import com.programmers.bucketback.domains.item.application.dto.AddMemberItemServiceRequest;
import com.programmers.bucketback.domains.item.application.dto.CreateItemServiceRequest;
import com.programmers.bucketback.domains.item.application.dto.EnrollItemServiceRequest;
import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnrollItemService {

	private final ItemAppender itemAppender;
	private final ItemService itemService;
	private final EnrollItemValidator enrollItemValidator;

	public void enrollItem(final EnrollItemServiceRequest request) {

		// 로그인 확인
		if (!MemberUtils.isLoggedIn()) {
			throw new BusinessException(ErrorCode.MEMBER_NOT_LOGIN);
		}

		// 중복 링크 체크
		enrollItemValidator.validItemURLNotDuplicated(request.itemUrl());

		// 웹 크롤링을 통한 아이템 정보 가져오기
		WebCrawler webCrawler = WebSite.selectCrawler(request.itemUrl());
		ItemInfo itemInfo = webCrawler.extractInfoFromUrl(request.itemUrl());

		// 아이템 등록
		CreateItemServiceRequest createItemServiceRequest = CreateItemServiceRequest.of(request.hobby(), itemInfo);
		Long enrolledItemId = itemAppender.append(createItemServiceRequest);

		// 아이템 담기
		AddMemberItemServiceRequest addMemberItemServiceRequest = getAddMemberItemServiceRequest(enrolledItemId);
		itemService.addItem(addMemberItemServiceRequest);
	}

	private AddMemberItemServiceRequest getAddMemberItemServiceRequest(final Long enrolledItemId) {
		List<Long> ids = new ArrayList<>();
		ids.add(enrolledItemId);

		return new AddMemberItemServiceRequest(
			ids
		);
	}
}
