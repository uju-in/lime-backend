package com.programmers.bucketback.domains.hobby;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.bucketback.domains.hobby.api.dto.response.HobbyGetResponse;
import com.programmers.bucketback.domains.hobby.application.HobbyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hobbies")
public class HobbyController {

	private final HobbyService hobbyService;

	@GetMapping
	public ResponseEntity<HobbyGetResponse> getHobby() {
		HobbyGetResponse response = hobbyService.getHobbies();
		return ResponseEntity.ok(response);
	}
}
