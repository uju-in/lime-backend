package com.programmers.bucketback.domains.hobby;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.bucketback.domains.hobby.api.dto.response.HobbyGetResponse;
import com.programmers.bucketback.domains.hobby.application.HobbyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "hobbies", description = "취미 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hobbies")
public class HobbyController {

	private final HobbyService hobbyService;

	@Operation(summary = "취미 목록 조회", description = "취미 목록 조회 결과를 반환 합니다.")
	@GetMapping
	public ResponseEntity<HobbyGetResponse> getHobby() {
		HobbyGetResponse response = hobbyService.getHobbies();

		return ResponseEntity.ok(response);
	}
}
