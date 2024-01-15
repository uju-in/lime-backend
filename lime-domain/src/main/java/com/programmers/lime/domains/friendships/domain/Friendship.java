package com.programmers.lime.domains.friendships.domain;

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
@Table(name = "friendships")
public class Friendship extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "to_member_id", nullable = false)
	private Long toMemberId;

	@Column(name = "from_member_id", nullable = false)
	private Long fromMemberId;

	public Friendship(
		final Long toMemberId,
		final Long fromMemberId
	) {
		this.toMemberId = toMemberId;
		this.fromMemberId = fromMemberId;
	}
}
