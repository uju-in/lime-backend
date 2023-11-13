package com.programmers.bucketback.domains.vote.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.programmers.bucketback.domains.common.BaseEntity;
import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.vote.domain.vo.Content;

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
import jakarta.validation.constraints.NotNull;
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

	@NotNull
	@Column(name = "member_id")
	private Long memberId;

	@NotNull
	@Column(name = "item1_id")
	private Long item1Id;

	@NotNull
	@Column(name = "item2_id")
	private Long item2Id;

	@NotNull
	@Column(name = "hobby")
	@Enumerated(EnumType.STRING)
	private Hobby hobby;

	@Embedded
	private Content content;

	@NotNull
	@Column(name = "start_time")
	private LocalDateTime startTime;

	@NotNull
	@Column(name = "end_time")
	private LocalDateTime endTime;

	@OneToMany(mappedBy = "vote", cascade = CascadeType.ALL)
	private List<Voter> voters = new ArrayList<>();

	@Builder
	private Vote(
		@NotNull final Long memberId,
		@NotNull final Long item1Id,
		@NotNull final Long item2Id,
		@NotNull final Hobby hobby,
		@NotNull final String content
	) {
		this.memberId = memberId;
		this.item1Id = item1Id;
		this.item2Id = item2Id;
		this.hobby = hobby;
		this.content = new Content(content);
		this.startTime = LocalDateTime.now();
		this.endTime = startTime.plusDays(1);
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
}
