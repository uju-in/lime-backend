package com.programmers.lime.domains.vote.model;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.programmers.lime.domains.vote.domain.Vote;
import com.programmers.lime.domains.vote.domain.VoteBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VoteCursorSummaryBuilder {
	public static List<VoteCursorSummary> buildMany(final int size) {
		final List<Vote> votes = VoteBuilder.buildMany(size);
		final List<VoteCursorSummary> voteCursorSummaries = new ArrayList<>();
		for (final Vote vote: votes) {
			final VoteInfo voteInfo = VoteInfo.builder()
				.id(vote.getId())
				.content(vote.getContent())
				.startTime(vote.getStartTime())
				.isVoting(false)
				.participants(vote.getVoters().size())
				.build();
			final String cursorId = generateCursorId(vote);
			final VoteCursorSummary voteCursorSummary = new VoteCursorSummary(voteInfo, vote.getItem1Id(), vote.getItem2Id(), cursorId);
			voteCursorSummaries.add(voteCursorSummary);
		}

		return voteCursorSummaries;
	}

	private static String generateCursorId(final Vote vote) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		final String formattedDate = vote.getStartTime().format(formatter);

		return String.format("%s%s", formattedDate, String.format("%08d", vote.getId()));
	}
}
