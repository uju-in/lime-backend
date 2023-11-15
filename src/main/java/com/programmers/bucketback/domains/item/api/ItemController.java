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

import com.programmers.bucketback.domains.common.vo.CursorRequest;
import com.programmers.bucketback.domains.item.api.dto.request.ItemEnrollRequest;
import com.programmers.bucketback.domains.item.api.dto.request.MemberItemAddRequest;
import com.programmers.bucketback.domains.item.api.dto.response.ItemAddResponse;
import com.programmers.bucketback.domains.item.api.dto.response.ItemEnrollResponse;
import com.programmers.bucketback.domains.item.api.dto.response.ItemGetByCursorResponse;
import com.programmers.bucketback.domains.item.api.dto.response.ItemGetNamesResponse;
import com.programmers.bucketback.domains.item.api.dto.response.ItemGetResponse;
import com.programmers.bucketback.domains.item.application.ItemEnrollService;
import com.programmers.bucketback.domains.item.application.ItemService;
import com.programmers.bucketback.domains.item.application.dto.ItemAddServiceResponse;
import com.programmers.bucketback.domains.item.application.dto.ItemGetByCursorServiceResponse;
import com.programmers.bucketback.domains.item.application.dto.ItemGetNamesServiceResponse;
import com.programmers.bucketback.domains.item.application.dto.ItemGetServiceResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController {

	private final ItemEnrollService itemEnrollService;

	private final ItemService itemService;

	@PostMapping("/enroll")
	public ResponseEntity<ItemEnrollResponse> enrollItem(@Valid @RequestBody final ItemEnrollRequest request) {
		Long enrolledItemId = itemEnrollService.enrollItem(request.toEnrollItemServiceRequest());
		ItemEnrollResponse response = new ItemEnrollResponse(enrolledItemId);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/myitems")
	public ResponseEntity<ItemAddResponse> addItems(@Valid @RequestBody final MemberItemAddRequest request) {
		ItemAddServiceResponse serviceResponse = itemService.addItem(request.toAddMemberItemServiceRequest());
		ItemAddResponse response = ItemAddResponse.from(serviceResponse);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{itemId}")
	public ResponseEntity<ItemGetResponse> getItem(@PathVariable final Long itemId) {
		ItemGetServiceResponse serviceResponse = itemService.getItem(itemId);

		ItemGetResponse response = ItemGetResponse.from(serviceResponse);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/myitems/{itemId}")
	public ResponseEntity<Void> deleteMyItem(@PathVariable final Long itemId) {
		itemService.removeMemberItem(itemId);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/item-names")
	public ResponseEntity<ItemGetNamesResponse> getItemNames(@RequestParam final String keyword) {
		ItemGetNamesServiceResponse serviceResponse = itemService.getItemNamesByKeyword(keyword);

		ItemGetNamesResponse response = ItemGetNamesResponse.from(serviceResponse);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/search")
	public ResponseEntity<ItemGetByCursorResponse> getItemsByCursor(
		@RequestParam final String keyword,
		@ModelAttribute("request") @Valid final CursorRequest request
	) {
		ItemGetByCursorServiceResponse serviceResponse = itemService.getItemsByCursor(
			keyword,
			request.toParameters()
		);

		ItemGetByCursorResponse response = ItemGetByCursorResponse.from(serviceResponse);
		return ResponseEntity.ok(response);
	}
}
