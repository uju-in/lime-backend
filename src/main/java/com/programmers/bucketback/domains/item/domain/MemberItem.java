package com.programmers.bucketback.domains.item.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "members_items")
public class MemberItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "members_id")
	private Long membersId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "items_id")
	private Item item;

	public MemberItem(
		@NotNull final Long membersId,
		final Item item
	) {
		this.membersId = membersId;
		this.item = item;
	}
}
