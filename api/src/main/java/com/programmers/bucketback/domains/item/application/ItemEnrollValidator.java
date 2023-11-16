package com.programmers.bucketback.domains.item.application;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.item.repository.ItemRepository;
import com.programmers.bucketback.error.exception.BusinessException;
import com.programmers.bucketback.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ItemEnrollValidator {

	private final ItemRepository itemRepository;

	public void validItemURLNotDuplicated(final String itemURL) {
		boolean isItemURLIsExist = itemRepository.existsItemsByUrl(itemURL);

		if (isItemURLIsExist) {
			throw new BusinessException(ErrorCode.ITEM_URL_ALREADY_EXIST);
		}
	}
}
