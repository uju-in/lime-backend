package com.programmers.bucketback.domains.vote.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.programmers.bucketback.domains.common.BaseEntity;
import com.programmers.bucketback.domains.common.Hobby;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
	@Column(name = "option1_item_id")
	private Long option1ItemId;

	@NotNull
	@Column(name = "option2_item_id")
	private Long option2ItemId;

	@NotNull
	@Column(name = "hobby")
	@Enumerated(EnumType.STRING)
	private Hobby hobby;

	@NotNull
	@Column(name = "content")
	private String content;

	@NotNull
	@Column(name = "start_time")
	private LocalDateTime startTime;

	@NotNull
	@Column(name = "end_time")
	private LocalDateTime endTime;

	@OneToMany(mappedBy = "vote", cascade = CascadeType.REMOVE)
	private List<Voter> voters = new ArrayList<>();

	@Builder
	private Vote(
		@NotNull final Long memberId,
		@NotNull final Long option1ItemId,
		@NotNull final Long option2ItemId,
		@NotNull final Hobby hobby,
		@NotNull final String content
	) {
		this.memberId = memberId;
		this.option1ItemId = option1ItemId;
		this.option2ItemId = option2ItemId;
		this.hobby = hobby;
		this.content = content;
		this.startTime = LocalDateTime.now();
		this.endTime = startTime.plusDays(1);
	}

	public boolean containsItem(final Long itemId) {
		return option1ItemId.equals(itemId) || option2ItemId.equals(itemId);
	}

	public void addVoter(final Voter voter) {
		this.voters.add(voter);
	}
}