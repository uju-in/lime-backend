package com.programmers.bucketback.domains.item.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.domains.item.api.dto.request.ItemEnrollRequest;
import com.programmers.bucketback.domains.item.api.dto.request.MemberItemAddRequest;
import com.programmers.bucketback.domains.item.api.dto.response.ItemAddResponse;
import com.programmers.bucketback.domains.item.api.dto.response.ItemEnrollResponse;
import com.programmers.bucketback.domains.item.api.dto.response.ItemGetByCursorResponse;
import com.programmers.bucketback.domains.item.api.dto.response.ItemGetNamesResponse;
import com.programmers.bucketback.domains.item.api.dto.response.ItemGetResponse;
import com.programmers.bucketback.domains.item.api.dto.response.MemberItemGetByCursorResponse;
import com.programmers.bucketback.domains.item.application.ItemEnrollService;
import com.programmers.bucketback.domains.item.application.ItemService;
import com.programmers.bucketback.domains.item.application.dto.ItemAddServiceResponse;
import com.programmers.bucketback.domains.item.application.dto.ItemGetNamesServiceResponse;
import com.programmers.bucketback.domains.item.application.dto.ItemGetServiceResponse;
import com.programmers.bucketback.domains.item.model.ItemCursorSummary;
import com.programmers.bucketback.domains.item.model.MemberItemSummary;
import com.programmers.bucketback.global.cursor.CursorRequest;

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

	@Operation(summary = "아이템 담기", description = "MemberItemAddRequest을 이용하여 사용자에 아이템을 담기 합니다.")
	@PostMapping("/myitems")
	public ResponseEntity<ItemAddResponse> addItems(@Valid @RequestBody final MemberItemAddRequest request) {
		ItemAddServiceResponse serviceResponse = itemService.addItem(request.toAddMemberItemServiceRequest());
		ItemAddResponse response = ItemAddResponse.from(serviceResponse);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "아이템 상세 조회", description = "itemId을 이용하여 아이템을 상세 조회 합니다.")
	@GetMapping("/{itemId}")
	public ResponseEntity<ItemGetResponse> getItem(@PathVariable final Long itemId) {
		ItemGetServiceResponse serviceResponse = itemService.getItem(itemId);

		ItemGetResponse response = ItemGetResponse.from(serviceResponse);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "나의 아이템 목록에서 삭제", description = "itemId을 이용하여 나의 아이템 목록에서 삭제 합니다.")
	@DeleteMapping("/myitems/{itemId}")
	public ResponseEntity<Void> deleteMyItem(@PathVariable final Long itemId) {
		itemService.removeMemberItem(itemId);

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "키워드로 아이템 이름 목록 조회", description = "키워드를 이용하여 아이템 이름 목록 조회 합니다")
	@GetMapping("/item-names")
	public ResponseEntity<ItemGetNamesResponse> getItemNames(@RequestParam final String keyword) {
		ItemGetNamesServiceResponse serviceResponse = itemService.getItemNamesByKeyword(keyword);

		ItemGetNamesResponse response = ItemGetNamesResponse.from(serviceResponse);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "아이템 검색 및 목록조회", description = "키워드를 이용하여 아이템 검색 및 목록조회 합니다.")
	@GetMapping("/search")
	public ResponseEntity<ItemGetByCursorResponse> getItemsByCursor(
		@RequestParam final String keyword,
		@ModelAttribute("request") @Valid final CursorRequest request
	) {
		CursorSummary<ItemCursorSummary> cursorSummary = itemService.getItemsByCursor(
			keyword,
			request.toParameters()
		);
		ItemGetByCursorResponse response = ItemGetByCursorResponse.from(cursorSummary);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "나의 아이템 목록 조회", description = "나의 아이템 목록을 조회 합니다.")
	@GetMapping("/myitems")
	public ResponseEntity<MemberItemGetByCursorResponse> getMemberItemsByCursor(
		@ModelAttribute("request") @Valid final CursorRequest request
	) {
		CursorSummary<MemberItemSummary> cursorSummary = itemService.getMemberItemsByCursor(
			request.toParameters()
		);
		MemberItemGetByCursorResponse response = MemberItemGetByCursorResponse.from(cursorSummary);

		return ResponseEntity.ok(response);
	}
}
