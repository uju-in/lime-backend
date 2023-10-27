package com.programmers.bucketback.domains.bucket.api;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.bucketback.domains.bucket.api.dto.request.BucketCreateRequest;
import com.programmers.bucketback.domains.bucket.application.BucketService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "buckets", description = "버킷 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BucketController {

	private final BucketService bucketService;

	@Operation(summary = "버킷 생성", description = "FundingCreateRequestDTO 을 이용하여 버킷을 생성힙니다.")
	@PostMapping("/buckets")
	public ResponseEntity createBucket(@RequestBody @Valid final BucketCreateRequest request){
		bucketService.createBucket(request.toContent()); //memberId값 필요함

		return ResponseEntity.ok().build();
	}


	@Operation(summary = "버킷 수정", description = "BucketId, BucketUpdateRequest 을 이용하여 버킷을 수정힙니다.")
	@PutMapping("/buckets/{bucketId}")
	public ResponseEntity updateBucket(
		@PathVariable(required = true) Long bucketId,
		@RequestBody @Valid final BucketUpdateRequest request
	){
		bucketService.modifyBucket(bucketId, request.toContent());

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "버킷 삭제", description = "BucketId을 이용하여 버킷을 삭제힙니다.")
	@DeleteMapping("/buckets/{bucketId}")
	public ResponseEntity deleteBucket(@PathVariable(required = true) Long bucketId){
		bucketService.deleteBucket(bucketId);

		return ResponseEntity.ok().build();
	}
}
