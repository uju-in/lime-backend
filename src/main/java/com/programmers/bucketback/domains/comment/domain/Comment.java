package com.programmers.bucketback.domains.comment.domain;

import com.programmers.bucketback.domains.common.BaseEntity;
import com.programmers.bucketback.domains.feed.domain.Feed;
import com.programmers.bucketback.domains.vote.domain.vo.Content;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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
public class Comment extends BaseEntity {

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

	@Embedded
	private Content content;

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
		this.content = new Content(content);
		this.adoption = false;
	}

	public boolean isInFeed(final Long feedId) {
		final Long thisFeedId = this.feed.getId();
		return thisFeedId.equals(feedId);
	}

	public boolean isOwner(final Long memberId) {
		return this.memberId.equals(memberId);
	}

	public void changeContent(final String content) {
		this.content = new Content(content);
	}

	public void adopt() {
		this.adoption = true;
	}
}
