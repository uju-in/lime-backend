package com.programmers.lime.domains.friendships.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.lime.domains.friendships.application.FriendshipService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "friendships", description = "친구 관계 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friendships")
public class FriendshipController {

	private final FriendshipService friendshipService;

	@Operation(summary = "팔로우", description = "팔로우 할 닉네임을 이용하여 팔로우 합니다.")
	@PostMapping("/follow/{nickname}")
	public ResponseEntity<Void> follow(@PathVariable final String nickname) {
		friendshipService.follow(nickname);

		return ResponseEntity.ok().build();
	}
}
