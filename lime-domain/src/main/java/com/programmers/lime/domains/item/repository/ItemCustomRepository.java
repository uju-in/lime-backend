package com.programmers.lime.domains.item.repository;

import com.programmers.lime.common.model.Hobby;

public interface ItemCustomRepository {

	Long countItem(String keyword, Hobby hobby);
}
