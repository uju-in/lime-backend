package com.programmers.lime.domains.item.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.programmers.lime.IntegrationTest;
import com.programmers.lime.domains.favorite.application.FavoriteItemService;
import com.programmers.lime.domains.favorite.application.dto.FavoriteItemCreateServiceResponse;
import com.programmers.lime.domains.item.domain.setup.ItemSetup;
import com.programmers.lime.domains.item.implementation.MemberItemValidator;
import com.programmers.lime.domains.item.model.FavoriteItemIdRegistry;
import com.programmers.lime.domains.item.model.MemberItemIdRegistryBuilder;
import com.programmers.lime.global.util.MemberUtils;

class ItemServiceTest extends IntegrationTest {

	@Autowired
	private ItemSetup itemSetup;

	@Autowired
	private FavoriteItemService favoriteItemService;

	@MockBean
	private MemberUtils memberUtils;

	@MockBean
	private MemberItemValidator memberItemValidator;

	@Test
	@DisplayName("나의 아이템을 추가한다.")
	public void addItemTest() {
		// given
		given(memberUtils.getCurrentMemberId()).willReturn(1L);

		// memberSetup이 만들어지면 변경할 예정
		doNothing().when(memberItemValidator).validateExistMemberItem(any(), any());

		FavoriteItemIdRegistry favoriteItemIdRegistryBuilder = MemberItemIdRegistryBuilder.build();
		FavoriteItemCreateServiceResponse expectedResponse = new FavoriteItemCreateServiceResponse(
			favoriteItemIdRegistryBuilder.itemIds());

		favoriteItemIdRegistryBuilder.itemIds()
			.forEach(itemId -> itemSetup.saveOne(itemId));

		// when
		FavoriteItemCreateServiceResponse actualResponse = favoriteItemService.createFavoriteItems(
			favoriteItemIdRegistryBuilder);

		// then
		assertThat(actualResponse)
			.usingRecursiveComparison()
			.isEqualTo(expectedResponse);
	}
}
