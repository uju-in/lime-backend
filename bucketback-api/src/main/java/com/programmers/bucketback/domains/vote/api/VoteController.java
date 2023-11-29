package com.programmers.bucketback.domains.vote.api;

import java.util.List;

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

import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.domains.vote.api.dto.request.VoteCreateRequest;
import com.programmers.bucketback.domains.vote.api.dto.request.VoteParticipateRequest;
import com.programmers.bucketback.domains.vote.api.dto.response.VoteCreateResponse;
import com.programmers.bucketback.domains.vote.api.dto.response.VoteGetByCursorResponse;
import com.programmers.bucketback.domains.vote.api.dto.response.VoteGetResponse;
import com.programmers.bucketback.domains.vote.api.dto.response.VoteRankResponse;
import com.programmers.bucketback.domains.vote.application.VoteService;
import com.programmers.bucketback.domains.vote.application.dto.response.VoteGetServiceResponse;
import com.programmers.bucketback.domains.vote.model.VoteSortCondition;
import com.programmers.bucketback.domains.vote.model.VoteStatusCondition;
import com.programmers.bucketback.domains.vote.model.VoteSummary;
import com.programmers.bucketback.global.cursor.CursorRequest;
import com.programmers.bucketback.redis.vote.VoteRedis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "votes", description = "투표 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/votes")
public class VoteController {

	private final VoteService voteService;

	@Operation(summary = "투표 생성", description = "VoteCreateRequest 을 이용하여 투표를 생성힙니다.")
	@PostMapping
	public ResponseEntity<VoteCreateResponse> createVote(@Valid @RequestBody final VoteCreateRequest request) {
		final Long voteId = voteService.createVote(request.toCreateVoteServiceRequest());
		final VoteCreateResponse response = new VoteCreateResponse(voteId);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "투표 참여", description = "VoteId, VoteParticipateRequest 을 이용하여 투표에 참여힙니다.")
	@PostMapping("/{voteId}/participation")
	public ResponseEntity<Void> participateVote(
		@PathVariable final Long voteId,
		@Valid @RequestBody final VoteParticipateRequest request
	) {
		voteService.participateVote(voteId, request.itemId());

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "투표 참여 취소", description = "VoteId를 이용하여 투표 참여를 취소합니다.")
	@DeleteMapping("/{voteId}/cancel")
	public ResponseEntity<Void> cancelVote(@PathVariable final Long voteId) {
		voteService.cancelVote(voteId);

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "투표 삭제", description = "VoteId를 이용하여 투표를 삭제합니다.")
	@DeleteMapping("/{voteId}")
	public ResponseEntity<Void> deleteVote(@PathVariable final Long voteId) {
		voteService.deleteVote(voteId);

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "투표 상세 조회", description = "VoteId를 이용하여 투표를 조회합니다.")
	@GetMapping("/{voteId}")
	public ResponseEntity<VoteGetResponse> getVote(@PathVariable final Long voteId) {
		final VoteGetServiceResponse serviceResponse = voteService.getVote(voteId);
		final VoteGetResponse response = VoteGetResponse.from(serviceResponse);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "투표 목록 조회(커서)", description = "취미, 상태 조건, 정렬 조건, 커서 요청 정보를 이용하여 투표를 조회합니다.")
	@GetMapping
	public ResponseEntity<VoteGetByCursorResponse> getVotesByCursor(
		@RequestParam final String hobby,
		@RequestParam(name = "status") final String statusCondition,
		@RequestParam(required = false, name = "sort") final String sortCondition,
		@ModelAttribute @Valid final CursorRequest request
	) {
		final CursorSummary<VoteSummary> cursorSummary = voteService.getVotesByCursor(
			Hobby.fromName(hobby),
			VoteStatusCondition.from(statusCondition),
			VoteSortCondition.from(sortCondition),
			request.toParameters()
		);
		final VoteGetByCursorResponse response = VoteGetByCursorResponse.from(cursorSummary);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "투표 검색", description = "keyword를 이용하여 투표를 검색합니다.")
	@GetMapping("/search")
	public ResponseEntity<VoteGetByCursorResponse> getVotesByCursor(
		@RequestParam final String keyword,
		@ModelAttribute @Valid final CursorRequest request
	) {
		final CursorSummary<VoteSummary> cursorSummary = voteService.getVotesByKeyword(keyword, request.toParameters());
		final VoteGetByCursorResponse response = VoteGetByCursorResponse.from(cursorSummary);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "랭킹 조회", description = "진행 중인 투표의 랭킹을 TOP 10까지 조회합니다.")
	@GetMapping("/ranking")
	public ResponseEntity<VoteRankResponse> rankVote() {
		final List<VoteRedis> voteRanking = voteService.rankVote();
		final VoteRankResponse response = VoteRankResponse.from(voteRanking);

		return ResponseEntity.ok(response);
	}
}
