package com.programmers.lime.domains.friendships.domain;

import java.util.Objects;

import com.programmers.lime.domains.BaseEntity;
import com.programmers.lime.domains.member.domain.Member;

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
@Table(name = "friendships")
public class Friendship extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "to_member_id", nullable = false)
	private Member toMember;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "from_member_id", nullable = false)
	private Member fromMember;

	public Friendship(
		final Member toMember,
		final Member fromMember
	) {
		this.toMember = Objects.requireNonNull(toMember);
		this.fromMember = Objects.requireNonNull(fromMember);
	}
}
