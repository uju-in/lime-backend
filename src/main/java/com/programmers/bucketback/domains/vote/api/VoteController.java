package com.programmers.bucketback.domains.vote.api;

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

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.common.vo.CursorRequest;
import com.programmers.bucketback.domains.vote.api.dto.request.VoteCreateRequest;
import com.programmers.bucketback.domains.vote.api.dto.request.VoteParticipateRequest;
import com.programmers.bucketback.domains.vote.api.dto.response.VoteCreateResponse;
import com.programmers.bucketback.domains.vote.api.dto.response.VoteGetByCursorResponse;
import com.programmers.bucketback.domains.vote.api.dto.response.VoteGetResponse;
import com.programmers.bucketback.domains.vote.application.VoteService;
import com.programmers.bucketback.domains.vote.application.dto.request.VoteSortCondition;
import com.programmers.bucketback.domains.vote.application.dto.request.VoteStatusCondition;
import com.programmers.bucketback.domains.vote.application.dto.response.GetVoteServiceResponse;
import com.programmers.bucketback.domains.vote.application.dto.response.GetVotesServiceResponse;

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
		final VoteCreateResponse response = new VoteCreateResponse(voteId);

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

	@DeleteMapping("/{voteId}/cancel")
	public ResponseEntity<Void> cancelVote(@PathVariable final Long voteId) {
		voteService.cancelVote(voteId);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{voteId}")
	public ResponseEntity<Void> deleteVote(@PathVariable final Long voteId) {
		voteService.deleteVote(voteId);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/{voteId}")
	public ResponseEntity<VoteGetResponse> getVote(@PathVariable final Long voteId) {
		final GetVoteServiceResponse voteServiceResponse = voteService.getVote(voteId);
		final VoteGetResponse response = VoteGetResponse.from(voteServiceResponse);

		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<VoteGetByCursorResponse> getVotesByCursor(
		@RequestParam final String hobby,
		@RequestParam(name = "status") final String statusCondition,
		@RequestParam(required = false, name = "sort") final String sortCondition,
		@ModelAttribute @Valid final CursorRequest request
	) {
		final GetVotesServiceResponse serviceResponse = voteService.getVotesByCursor(
			Hobby.valueOf(hobby.toUpperCase()),
			VoteStatusCondition.from(statusCondition),
			VoteSortCondition.from(sortCondition),
			request.toParameters()
		);
		final VoteGetByCursorResponse response = VoteGetByCursorResponse.from(serviceResponse);

		return ResponseEntity.ok(response);
	}

}
