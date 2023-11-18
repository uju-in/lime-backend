package com.programmers.bucketback.domains.item.application;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.item.implementation.ItemReader;
import com.programmers.bucketback.error.BusinessException;
import com.programmers.bucketback.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ItemEnrollValidator {

	private final ItemReader itemReader;

	public void validItemURLNotDuplicated(final String itemURL) {
		boolean isItemURLIsExist = itemReader.existsItemsByUrl(itemURL);

		if (isItemURLIsExist) {
			throw new BusinessException(ErrorCode.ITEM_URL_ALREADY_EXIST);
		}
	}
}
