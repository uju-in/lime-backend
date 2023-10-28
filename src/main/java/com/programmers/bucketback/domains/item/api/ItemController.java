package com.programmers.bucketback.domains.item.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.bucketback.domains.item.api.dto.request.ItemEnrollRequest;
import com.programmers.bucketback.domains.item.api.dto.request.MemberItemAddRequest;
import com.programmers.bucketback.domains.item.application.EnrollItemService;
import com.programmers.bucketback.domains.item.application.ItemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController {

	private final EnrollItemService enrollItemService;

	private final ItemService itemService;

	@PostMapping("/enroll")
	public ResponseEntity<Void> enrollItem(@Valid @RequestBody final ItemEnrollRequest request) {
		enrollItemService.enrollItem(request.toEnrollItemServiceRequest());

		return ResponseEntity.ok().build();
	}

	@PostMapping("/myItem")
	public ResponseEntity<Void> addItems(@Valid @RequestBody final MemberItemAddRequest request) {
		itemService.addItem(request.toAddMemberItemServiceRequest());

		return ResponseEntity.ok().build();
	}
}
