package com.programmers.bucketback.domains.bucket.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.bucketback.domains.bucket.application.BucketService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BucketController {

	private final BucketService bucketService;

	@GetMapping("/buckets")
	public ResponseEntity createBucket(@RequestBody @Valid final BucketCreateRequest request){
		bucketService.createBucket(request.toContent()); //memberId값 필요함

		return ResponseEntity.ok().build();
	}


}
