package com.programmers.lime.domains.item.api;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.lime.domains.item.api.dto.request.ItemEnrollRequest;
import com.programmers.lime.domains.item.api.dto.request.ItemSearchRequest;
import com.programmers.lime.domains.item.api.dto.response.ItemEnrollResponse;
import com.programmers.lime.domains.item.api.dto.response.ItemGetByCursorResponse;
import com.programmers.lime.domains.item.api.dto.response.ItemGetNamesResponse;
import com.programmers.lime.domains.item.api.dto.response.ItemGetRankingResponse;
import com.programmers.lime.domains.item.api.dto.response.ItemGetResponse;
import com.programmers.lime.domains.item.application.ItemEnrollService;
import com.programmers.lime.domains.item.application.ItemService;
import com.programmers.lime.domains.item.application.dto.ItemGetByCursorServiceResponse;
import com.programmers.lime.domains.item.application.dto.ItemGetNamesServiceResponse;
import com.programmers.lime.domains.item.application.dto.ItemGetServiceResponse;
import com.programmers.lime.global.cursor.CursorRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "items", description = "아이템 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController {

	private final ItemEnrollService itemEnrollService;

	private final ItemService itemService;

	@Operation(summary = "아이템 등록", description = "ItemEnrollRequest 을 이용하여 아이템을 등록합니다.")
	@PostMapping("/enroll")
	public ResponseEntity<ItemEnrollResponse> enrollItem(@Valid @RequestBody final ItemEnrollRequest request) {
		Long enrolledItemId = itemEnrollService.enrollItem(request.toEnrollItemServiceRequest());
		ItemEnrollResponse response = new ItemEnrollResponse(enrolledItemId);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "아이템 상세 조회", description = "itemId을 이용하여 아이템을 상세 조회 합니다.")
	@GetMapping("/{itemId}")
	public ResponseEntity<ItemGetResponse> getItem(@PathVariable final Long itemId) {
		ItemGetServiceResponse serviceResponse = itemService.getItem(itemId);

		ItemGetResponse response = ItemGetResponse.from(serviceResponse);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "아이템 이름 목록 조회", description = "키워드를 이용하여 아이템 이름 목록 조회 합니다")
	@GetMapping("/item-names")
	public ResponseEntity<ItemGetNamesResponse> getItemNames(@RequestParam final String keyword) {
		ItemGetNamesServiceResponse serviceResponse = itemService.getItemNamesByKeyword(keyword);

		ItemGetNamesResponse response = ItemGetNamesResponse.from(serviceResponse);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "아이템 목록조회", description = "키워드, 취미를 이용하여 아이템 목록조회 합니다.")
	@GetMapping("/search")
	public ResponseEntity<ItemGetByCursorResponse> getItemsByCursor(
		@ParameterObject @ModelAttribute("request") @Valid final CursorRequest request,
		@ParameterObject @ModelAttribute @Valid final ItemSearchRequest searchRequest
	) {
		ItemGetByCursorServiceResponse serviceResponse = itemService.getItemsByCursor(
			searchRequest.keyword(),
			request.toParameters(),
			searchRequest.itemSortCondition(),
			searchRequest.hobbyName()
		);
		ItemGetByCursorResponse response = ItemGetByCursorResponse.from(serviceResponse);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "랭킹 조회", description = "랭킹을 TOP10까지 조회합니다.")
	@GetMapping("/ranking")
	public ResponseEntity<ItemGetRankingResponse> getRanking() {
		ItemGetRankingResponse response = ItemGetRankingResponse.from(itemService.getRanking());

		return ResponseEntity.ok(response);
	}
}
