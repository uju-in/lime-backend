package com.programmers.lime.domains.comment.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.lime.domains.comment.domain.Comment;
import com.programmers.lime.domains.comment.domain.CommentBuilder;
import com.programmers.lime.domains.comment.repository.CommentRepository;
import com.programmers.lime.domains.feed.FeedBuilder;
import com.programmers.lime.domains.feed.domain.Feed;
import com.programmers.lime.domains.feed.implementation.FeedReader;

@ExtendWith(MockitoExtension.class)
class CommentAppenderTest {

	@InjectMocks
	private CommentAppender commentAppender;

	@Mock
	private CommentRepository commentRepository;

	@Mock
	private FeedReader feedReader;

	@Test
	@DisplayName("댓글을 생성한다.")
	void appendTest() {
		// given
		final Feed feed = FeedBuilder.build();
		final String content = "이거 좀 짱인듯";
		final Comment comment = CommentBuilder.build(feed, content);

		given(feedReader.read(anyLong()))
			.willReturn(feed);

		given(commentRepository.save(any(Comment.class)))
			.willReturn(comment);

		// when
		final Comment savedComment = commentAppender.append(feed.getId(), content, anyLong());

		// then
		assertThat(savedComment).usingRecursiveComparison()
			.isEqualTo(comment);
	}
}
