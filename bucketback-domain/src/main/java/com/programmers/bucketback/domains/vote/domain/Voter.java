package com.programmers.bucketback.domains.vote.domain;

import java.util.Objects;

import com.programmers.bucketback.domains.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "voters")
public class Voter extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vote_id", nullable = false)
	private Vote vote;

	@Column(name = "member_id", nullable = false)
	private Long memberId;

	@Column(name = "item_id", nullable = false)
	private Long itemId;

	public Voter(
		final Vote vote,
		final Long memberId,
		final Long itemId
	) {
		this.vote = Objects.requireNonNull(vote);
		this.memberId = Objects.requireNonNull(memberId);
		this.itemId = Objects.requireNonNull(itemId);
	}

	public void participate(final Long itemId) {
		this.itemId = itemId;
		this.vote.addVoter(this);
	}
}
