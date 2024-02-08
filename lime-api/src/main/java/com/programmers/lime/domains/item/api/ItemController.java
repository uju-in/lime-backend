package com.programmers.lime.domains.item.api;

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

import com.programmers.lime.domains.item.api.dto.request.FavoriteItemDeleteRequest;
import com.programmers.lime.domains.item.api.dto.request.ItemEnrollRequest;
import com.programmers.lime.domains.item.api.dto.request.MemberItemCreateRequest;
import com.programmers.lime.domains.item.api.dto.request.MemberItemFolderCreateRequest;
import com.programmers.lime.domains.item.api.dto.request.MemberItemFolderUpdateRequest;
import com.programmers.lime.domains.item.api.dto.request.MemberItemMoveRequest;
import com.programmers.lime.domains.item.api.dto.response.MemberItemCreateResponse;
import com.programmers.lime.domains.item.api.dto.response.ItemEnrollResponse;
import com.programmers.lime.domains.item.api.dto.response.ItemGetByCursorResponse;
import com.programmers.lime.domains.item.api.dto.response.ItemGetNamesResponse;
import com.programmers.lime.domains.item.api.dto.response.ItemGetRankingResponse;
import com.programmers.lime.domains.item.api.dto.response.ItemGetResponse;
import com.programmers.lime.domains.item.api.dto.response.MemberItemFavoritesGetResponse;
import com.programmers.lime.domains.item.application.ItemEnrollService;
import com.programmers.lime.domains.item.application.ItemService;
import com.programmers.lime.domains.item.application.MemberItemFolderService;
import com.programmers.lime.domains.item.application.dto.MemberItemCreateServiceResponse;
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

	private final MemberItemFolderService memberItemFolderService;

	@Operation(summary = "아이템 등록", description = "ItemEnrollRequest 을 이용하여 아이템을 등록합니다.")
	@PostMapping("/enroll")

	public ResponseEntity<ItemEnrollResponse> enrollItem(@Valid @RequestBody final ItemEnrollRequest request) {
		Long enrolledItemId = itemEnrollService.enrollItem(request.toEnrollItemServiceRequest());
		ItemEnrollResponse response = new ItemEnrollResponse(enrolledItemId);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "찜 목록에 아이템 넣기", description = "MemberItemAddRequest을 이용하여 사용자의 찜 목록에 아이템 담기 합니다.")
	@PostMapping("/myitems")
	public ResponseEntity<MemberItemCreateResponse> createMemberItems(
		@Valid @RequestBody final MemberItemCreateRequest request
	) {
		MemberItemCreateServiceResponse serviceResponse = itemService.createMemberItems(
			request.toMemberItemIdRegistry()
		);
		MemberItemCreateResponse response = MemberItemCreateResponse.from(serviceResponse);

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
		@RequestParam final String keyword,
		@ModelAttribute("request") @Valid final CursorRequest request,
		@RequestParam(required = false) final String itemSortCondition,
		@RequestParam(required = false) final String hobbyName
	) {
		ItemGetByCursorServiceResponse serviceResponse = itemService.getItemsByCursor(
			keyword,
			request.toParameters(),
			itemSortCondition,
			hobbyName
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

	@Operation(summary = "찜 목록폴더 생성", description = "찜 목록 폴더를 생성 합니다.")
	@PostMapping("/myitems/folders")
	public ResponseEntity<Void> addMemberItemFolder(
		@RequestBody @Valid final MemberItemFolderCreateRequest request
	) {
		memberItemFolderService.createMemberItemFolder(
			request.folderName()
		);

		return ResponseEntity.ok().build();
	}

  	@Operation(summary = "찜 목록 폴더 수정", description = "찜 목록 폴더를 수정 합니다.")
	@PutMapping("/myitems/folders/{folderId}")
	public ResponseEntity<Void> modifyMemberItemFolder(
		@PathVariable final Long folderId,
		@RequestBody @Valid final MemberItemFolderUpdateRequest request
	) {
		memberItemFolderService.modifyMemberItemFolder(
			folderId,
			request.folderName()
		);

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "찜 항목 제거", description = "찜 목록으로 부터 아이템이나 폴더를 제거 합니다.")
	@DeleteMapping("/myitems")
	public ResponseEntity<Void> removeFavorite(
		@ModelAttribute @Valid final FavoriteItemDeleteRequest request
	) {
		itemService.removeMemberItems(request.itemIds());
		memberItemFolderService.removeMemberItemFolders(request.folderIds());

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "찜 목록 조회", description = "찜 목록을 조회 합니다.")
	@GetMapping("/myitems")
	public ResponseEntity<MemberItemFavoritesGetResponse> getMemberItemObjects(
		@RequestParam(required = false) final Long folderId
	) {
		MemberItemFavoritesGetResponse response = itemService.getMemberItemFavorites(
			folderId
		);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "찜 이동", description = "찜을 다른 폴더로 이동 합니다.")
	@PutMapping("/myitems/move")
	public ResponseEntity<Void> moveMemberItems(
		@RequestBody @Valid final MemberItemMoveRequest request
	) {
		itemService.moveMemberItems(
			request.folderId(),
			request.memberItemIds()
		);

		return ResponseEntity.ok().build();
	}
}
