package com.programmers.lime.domains.favorite.api;

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

import com.programmers.lime.domains.favorite.api.dto.request.FavoriteItemDeleteRequest;
import com.programmers.lime.domains.favorite.api.dto.request.MemberItemCreateRequest;
import com.programmers.lime.domains.favorite.api.dto.request.MemberItemFolderCreateRequest;
import com.programmers.lime.domains.favorite.api.dto.request.MemberItemFolderUpdateRequest;
import com.programmers.lime.domains.favorite.api.dto.request.MemberItemMoveRequest;
import com.programmers.lime.domains.favorite.api.dto.response.MemberItemCreateResponse;
import com.programmers.lime.domains.favorite.api.dto.response.MemberItemFavoritesGetResponse;
import com.programmers.lime.domains.favorite.application.FavoriteItemService;
import com.programmers.lime.domains.favorite.application.MemberItemFolderService;
import com.programmers.lime.domains.favorite.application.dto.MemberItemCreateServiceResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "favorite", description = "찜 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorite")
public class FavoriteItemController {

	private final FavoriteItemService favoriteItemService;

	private final MemberItemFolderService memberItemFolderService;

	@Operation(summary = "찜 ", description = "MemberItemAddRequest을 이용하여 사용자의 찜 목록에 아이템 담기 합니다.")
	@PostMapping("/item")
	public ResponseEntity<MemberItemCreateResponse> createMemberItems(
		@Valid @RequestBody final MemberItemCreateRequest request
	) {
		MemberItemCreateServiceResponse serviceResponse = favoriteItemService.createMemberItems(
			request.toMemberItemIdRegistry()
		);
		MemberItemCreateResponse response = MemberItemCreateResponse.from(serviceResponse);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "찜 목록 조회", description = "찜 목록을 조회 합니다.")
	@GetMapping()
	public ResponseEntity<MemberItemFavoritesGetResponse> getMemberItemObjects(
		@RequestParam(required = false) final Long folderId
	) {
		MemberItemFavoritesGetResponse response = favoriteItemService.getMemberItemFavorites(
			folderId
		);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "찜 아이템 이동", description = "찜 아이템을 다른 폴더로 이동 합니다.")
	@PutMapping("/item/move")
	public ResponseEntity<Void> moveMemberItems(
		@RequestBody @Valid final MemberItemMoveRequest request
	) {
		favoriteItemService.moveMemberItems(
			request.folderId(),
			request.favoriteItemIds()
		);

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "찜 항목 제거", description = "찜 목록으로 부터 아이템이나 폴더를 제거 합니다.")
	@DeleteMapping()
	public ResponseEntity<Void> removeFavorite(
		@ModelAttribute @Valid final FavoriteItemDeleteRequest request
	) {
		favoriteItemService.removeMemberItems(request.favoriteItemIds());
		memberItemFolderService.removeMemberItemFolders(request.folderIds());

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "찜 목록 폴더 생성", description = "찜 목록 폴더를 생성 합니다.")
	@PostMapping("/folders")
	public ResponseEntity<Void> addMemberItemFolder(
		@RequestBody @Valid final MemberItemFolderCreateRequest request
	) {
		memberItemFolderService.createMemberItemFolder(
			request.folderName()
		);

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "찜 목록 폴더 이름 수정", description = "찜 목록 폴더 이름을 수정 합니다.")
	@PutMapping("/folders/{folderId}")
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
}
