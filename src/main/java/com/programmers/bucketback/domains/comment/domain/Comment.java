package com.programmers.bucketback.domains.comment.domain;

import com.programmers.bucketback.domains.feed.domain.Feed;

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
@Table(name = "comments")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "feed_id")
	private Feed feed;

	@NotNull
	@Column(name = "member_id")
	private Long memberId;

	@NotNull
	@Column(name = "content")
	private String content;

	@NotNull
	@Column(name = "adoption", columnDefinition = "TINYINT(1)")
	private boolean adoption;

	public Comment(
		@NotNull final Feed feed,
		@NotNull final Long memberId,
		@NotNull final String content
	) {
		this.feed = feed;
		this.memberId = memberId;
		this.content = content;
		this.adoption = false;
	}
}
