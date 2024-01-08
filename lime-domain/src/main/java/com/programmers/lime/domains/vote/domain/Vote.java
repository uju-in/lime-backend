package com.programmers.lime.domains.vote.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.BaseEntity;
import com.programmers.lime.domains.vote.domain.vo.Content;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "votes")
public class Vote extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "member_id", nullable = false)
	private Long memberId;

	@Column(name = "item1_id", nullable = false)
	private Long item1Id;

	@Column(name = "item2_id", nullable = false)
	private Long item2Id;

	@Column(name = "hobby", nullable = false)
	@Enumerated(EnumType.STRING)
	private Hobby hobby;

	@Embedded
	private Content content;

	@Column(name = "start_time", nullable = false)
	private LocalDateTime startTime;

	@Column(name = "end_time", nullable = false)
	private LocalDateTime endTime;

	@Column(name = "maximum_participants")
	private Integer maximumParticipants;

	@OneToMany(mappedBy = "vote", cascade = CascadeType.ALL)
	private List<Voter> voters = new ArrayList<>();

	@Builder
	private Vote(
		final Long memberId,
		final Long item1Id,
		final Long item2Id,
		final Hobby hobby,
		final String content,
		final Integer maximumParticipants
	) {
		this.memberId = Objects.requireNonNull(memberId);
		this.item1Id = Objects.requireNonNull(item1Id);
		this.item2Id = Objects.requireNonNull(item2Id);
		this.hobby = Objects.requireNonNull(hobby);
		this.content = new Content(content);
		this.startTime = LocalDateTime.now();
		this.endTime = startTime.plusDays(1);
		this.maximumParticipants = maximumParticipants;
	}

	public String getContent() {
		return content.getContent();
	}

	public boolean containsItem(final Long itemId) {
		return item1Id.equals(itemId) || item2Id.equals(itemId);
	}

	public void addVoter(final Voter voter) {
		if (!this.voters.contains(voter)) {
			this.voters.add(voter);
		}
	}

	public boolean isOwner(final Long memberId) {
		return this.memberId.equals(memberId);
	}

	public boolean isVoting() {
		final LocalDateTime now = LocalDateTime.now();
		return this.endTime.isAfter(now);
	}

	public void close(final LocalDateTime now) {
		this.endTime = now;
	}

	public boolean reachMaximumParticipants() {
		return this.maximumParticipants != null && this.maximumParticipants == this.voters.size();
	}
}
