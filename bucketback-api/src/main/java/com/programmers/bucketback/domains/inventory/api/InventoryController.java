package com.programmers.bucketback.domains.inventory.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.domains.inventory.api.dto.request.InventoryCreateRequest;
import com.programmers.bucketback.domains.inventory.api.dto.request.InventoryUpdateRequest;
import com.programmers.bucketback.domains.inventory.api.dto.response.InventoriesGetResponse;
import com.programmers.bucketback.domains.inventory.api.dto.response.InventoryCreateResponse;
import com.programmers.bucketback.domains.inventory.api.dto.response.InventoryGetResponse;
import com.programmers.bucketback.domains.inventory.api.dto.response.InventoryGetReviewedItemResponse;
import com.programmers.bucketback.domains.inventory.application.InventoryService;
import com.programmers.bucketback.global.cursor.CursorRequest;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class InventoryController {

	private final InventoryService inventoryService;

	@Operation(summary = "인벤토리 생성", description = "InventoryCreateRequestDTO 을 이용하여 버킷을 생성힙니다.")
	@PostMapping("/inventories")
	public ResponseEntity<InventoryCreateResponse> createInventory(
		@RequestBody @Valid final InventoryCreateRequest request) {
		Hobby hobby = Hobby.fromHobbyValue(request.hobbyValue());
		Long inventoryId = inventoryService.createInventory(hobby, request.toRegistry());

		return ResponseEntity.ok(new InventoryCreateResponse(inventoryId));
	}

	@Operation(summary = "인벤토리 수정", description = "InventoryUpdateRequestDTO 을 이용하여 인벤토리를 업데이트힙니다.")
	@PutMapping("/inventories/{inventoryId}")
	public ResponseEntity<Void> modifyInventory(
		@PathVariable final Long inventoryId,
		@RequestBody @Valid final InventoryUpdateRequest request
	) {
		inventoryService.modifyInventory(inventoryId, request.toRegistry());

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "인벤토리 삭제", description = "InventoryId를 이용하여 버킷을 삭제힙니다.")
	@DeleteMapping("/inventories/{inventoryId}")
	public ResponseEntity<Void> deleteInventory(
		@PathVariable final Long inventoryId
	) {
		inventoryService.deleteInventory(inventoryId);

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "인벤토리 상세 조회", description = "InventoryId을 이용하여 인벤토리를 조회힙니다.")
	@GetMapping("/{nickname}/inventories/{inventoryId}")
	public ResponseEntity<InventoryGetResponse> getInventory(
		@PathVariable final String nickname,
		@PathVariable final Long inventoryId
	) {
		InventoryGetResponse response = InventoryGetResponse.from(
			inventoryService.getInventory(inventoryId)
		);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "인벤토리 조회와 수정을 위한 내가 리뷰한 아이템 조회")
	@GetMapping("/inventories/myitems")
	public ResponseEntity<InventoryGetReviewedItemResponse> getReviewedItemsForModify(
		@RequestParam(required = false) final Long inventoryId,
		@RequestParam(required = false) final String hobbyName,
		@ModelAttribute @Valid final CursorRequest cursorRequest
	) {

		InventoryGetReviewedItemResponse response = InventoryGetReviewedItemResponse.from(
			inventoryService.getReviewedItemsForModify(
				inventoryId,
				Hobby.fromName(hobbyName),
				cursorRequest.toParameters()
			)
		);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "인벤토리 목록 조회", description = "닉네임으로 유저의 인벤토리 목록을 조회한다.")
	@GetMapping("/{nickname}/inventories")
	public ResponseEntity<InventoriesGetResponse> getInventories(
		@PathVariable final String nickname
	) {
		InventoriesGetResponse response = InventoriesGetResponse.from(
			inventoryService.getInventories(nickname)
		);

		return ResponseEntity.ok(response);
	}
}
