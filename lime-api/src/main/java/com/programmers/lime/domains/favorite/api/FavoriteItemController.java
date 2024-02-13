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

import com.programmers.lime.domains.favorite.api.dto.request.FavoriteRemoveRequest;
import com.programmers.lime.domains.favorite.api.dto.request.FavoriteItemCreateRequest;
import com.programmers.lime.domains.favorite.api.dto.request.FolderCreateRequest;
import com.programmers.lime.domains.favorite.api.dto.request.FolderUpdateRequest;
import com.programmers.lime.domains.favorite.api.dto.request.MemberItemMoveRequest;
import com.programmers.lime.domains.favorite.api.dto.response.FavoriteItemCreateResponse;
import com.programmers.lime.domains.favorite.api.dto.response.FavoritesGetResponse;
import com.programmers.lime.domains.favorite.application.FavoriteItemService;
import com.programmers.lime.domains.favorite.application.FolderService;
import com.programmers.lime.domains.favorite.application.dto.FavoriteItemCreateServiceResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "favorites", description = "찜 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
public class FavoriteItemController {

	private final FavoriteItemService favoriteItemService;

	private final FolderService folderService;

	@Operation(summary = "찜 ", description = "MemberItemAddRequest을 이용하여 사용자의 찜 목록에 아이템 담기 합니다.")
	@PostMapping("/items")
	public ResponseEntity<FavoriteItemCreateResponse> createFavoriteItems(
		@Valid @RequestBody final FavoriteItemCreateRequest request
	) {
		FavoriteItemCreateServiceResponse serviceResponse = favoriteItemService.createFavoriteItems(
			request.toFavoriteItemIdRegistry()
		);
		FavoriteItemCreateResponse response = FavoriteItemCreateResponse.from(serviceResponse);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "찜 목록 조회", description = "찜 목록을 조회 합니다.")
	@GetMapping()
	public ResponseEntity<FavoritesGetResponse> getFavorites(
		@RequestParam(required = false) final Long folderId
	) {
		FavoritesGetResponse response = favoriteItemService.getFavorites(
			folderId
		);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "찜 아이템 이동", description = "찜 아이템을 다른 폴더로 이동 합니다.")
	@PutMapping("/items/move")
	public ResponseEntity<Void> moveFavoriteItems(
		@RequestBody @Valid final MemberItemMoveRequest request
	) {
		favoriteItemService.moveFavoriteItems(
			request.folderId(),
			request.favoriteItemIds()
		);

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "찜 항목 제거", description = "찜 목록으로 부터 아이템이나 폴더를 제거 합니다.")
	@DeleteMapping()
	public ResponseEntity<Void> removeFavorite(
		@ModelAttribute @Valid final FavoriteRemoveRequest request
	) {
		favoriteItemService.removeFavoriteItems(request.favoriteItemIds());
		folderService.removeFolders(request.folderIds());

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "찜 목록 폴더 생성", description = "찜 목록 폴더를 생성 합니다.")
	@PostMapping("/folders")
	public ResponseEntity<Void> addMemberItemFolder(
		@RequestBody @Valid final FolderCreateRequest request
	) {
		folderService.createMemberItemFolder(
			request.folderName()
		);

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "찜 목록 폴더 이름 수정", description = "찜 목록 폴더 이름을 수정 합니다.")
	@PutMapping("/folders/{folderId}")
	public ResponseEntity<Void> modifyFolder(
		@PathVariable final Long folderId,
		@RequestBody @Valid final FolderUpdateRequest request
	) {
		folderService.modifyFolder(
			folderId,
			request.folderName()
		);

		return ResponseEntity.ok().build();
	}
}
