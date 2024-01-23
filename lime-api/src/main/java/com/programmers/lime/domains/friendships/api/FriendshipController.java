package com.programmers.lime.domains.friendships.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.domains.friendships.api.dto.response.FriendshipGetByCursorResponse;
import com.programmers.lime.domains.friendships.application.FriendshipService;
import com.programmers.lime.domains.friendships.model.FriendshipSummary;
import com.programmers.lime.global.cursor.CursorRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

	@Operation(summary = "언팔로우", description = "언팔로우 할 닉네임을 이용하여 팔로우를 취소합니다.")
	@PostMapping("/unfollow/{nickname}")
	public ResponseEntity<Void> unfollow(@PathVariable final String nickname) {
		friendshipService.unfollow(nickname);

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "팔로워 목록 조회", description = "회원의 팔로워 목록을 조회한다.")
	@GetMapping("/follower/{nickname}")
	public ResponseEntity<FriendshipGetByCursorResponse> getFollower(
		@PathVariable final String nickname,
		@ModelAttribute @Valid final CursorRequest request
	) {
		final CursorSummary<FriendshipSummary> cursorSummary = friendshipService.getFollower(
			nickname,
			request.toParameters()
		);
		final FriendshipGetByCursorResponse response = FriendshipGetByCursorResponse.from(cursorSummary);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "팔로잉 목록 조회", description = "회원의 팔로잉 목록을 조회한다.")
	@GetMapping("/following/{nickname}")
	public ResponseEntity<FriendshipGetByCursorResponse> getFollowing(
		@PathVariable final String nickname,
		@ModelAttribute @Valid final CursorRequest request
	) {
		final CursorSummary<FriendshipSummary> cursorSummary = friendshipService.getFollowing(
			nickname,
			request.toParameters()
		);
		final FriendshipGetByCursorResponse response = FriendshipGetByCursorResponse.from(cursorSummary);

		return ResponseEntity.ok(response);
	}
}
