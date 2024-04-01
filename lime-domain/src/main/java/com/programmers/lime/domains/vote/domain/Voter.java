package com.programmers.lime.domains.vote.domain;

import java.util.Objects;

import com.programmers.lime.domains.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	@Column(name = "vote_id", nullable = false)
	private Long voteId;

	@Column(name = "member_id", nullable = false)
	private Long memberId;

	@Column(name = "item_id", nullable = false)
	private Long itemId;

	public Voter(
		final Long voteId,
		final Long memberId,
		final Long itemId
	) {
		this.voteId = Objects.requireNonNull(voteId);
		this.memberId = Objects.requireNonNull(memberId);
		this.itemId = Objects.requireNonNull(itemId);
	}

	public void participate(final Long itemId) {
		this.itemId = itemId;
	}
}
