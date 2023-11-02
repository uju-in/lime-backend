package com.programmers.bucketback.domains.inventory.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.bucketback.domains.inventory.api.dto.request.InventoryCreateRequest;
import com.programmers.bucketback.domains.inventory.application.InventoryService;

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
	public ResponseEntity<Void> createInventory(@RequestBody @Valid final InventoryCreateRequest request) {
		inventoryService.createInventory(request.toContent());

		return ResponseEntity.ok().build();
	}
}
