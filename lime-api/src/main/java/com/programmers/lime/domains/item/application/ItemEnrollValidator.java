package com.programmers.lime.domains.item.application;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.item.implementation.ItemReader;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;

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
