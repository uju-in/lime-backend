package com.programmers.bucketback.domains.vote.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.bucketback.domains.vote.api.dto.request.VoteCreateRequest;
import com.programmers.bucketback.domains.vote.api.dto.request.VoteParticipateRequest;
import com.programmers.bucketback.domains.vote.api.dto.response.VoteCreateResponse;
import com.programmers.bucketback.domains.vote.application.VoteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/votes")
public class VoteController {

	private final VoteService voteService;

	@PostMapping
	public ResponseEntity<VoteCreateResponse> createVote(@Valid @RequestBody final VoteCreateRequest request) {
		final Long voteId = voteService.createVote(request.toCreateVoteServiceRequest());
		VoteCreateResponse response = new VoteCreateResponse(voteId);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/{voteId}/participation")
	public ResponseEntity<Void> participateVote(
		@PathVariable final Long voteId,
		@Valid @RequestBody final VoteParticipateRequest request
	) {
		voteService.participateVote(voteId, request.itemId());

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("{voteId}")
	public ResponseEntity<Void> deleteVote(@PathVariable final Long voteId) {
		voteService.deleteVote(voteId);

		return ResponseEntity.ok().build();
	}
}
