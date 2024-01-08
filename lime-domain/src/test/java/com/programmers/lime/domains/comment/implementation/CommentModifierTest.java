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

@ExtendWith(MockitoExtension.class)
class CommentModifierTest {

	@InjectMocks
	private CommentModifier commentModifier;

	@Mock
	private CommentReader commentReader;

	@Test
	@DisplayName("댓글을 수정한다.")
	void modifyTest() {
		// given
		final Comment comment = CommentBuilder.build("이거 좀 짱인듯");
		final String newContent = "우와 대박이네!";

		given(commentReader.read(anyLong()))
			.willReturn(comment);

		// when
		commentModifier.modify(comment.getId(), newContent);

		// then
		assertThat(comment.getContent()).isEqualTo(newContent);
	}

	@Test
	@DisplayName("댓글을 채택한다.")
	void adoptTest() {
		// given
		final Comment comment = CommentBuilder.build();

		// when
		commentModifier.adopt(comment);

		// then
		assertThat(comment.isAdoption()).isTrue();
	}
}
