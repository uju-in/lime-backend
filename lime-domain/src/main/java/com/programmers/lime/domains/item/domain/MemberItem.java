package com.programmers.lime.domains.item.domain;

import java.util.Objects;

import com.programmers.lime.domains.BaseEntity;

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
@Table(name = "members_items")
public class MemberItem extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "member_id", nullable = false)
	private Long memberId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id", nullable = false)
	private Item item;

	@Column(name = "folder_id")
	private Long folderId;

	public MemberItem(
		final Long memberId,
		final Item item,
		final Long folderId
	) {
		this.memberId = Objects.requireNonNull(memberId);
		this.item = Objects.requireNonNull(item);
		this.folderId = folderId;
	}
}
