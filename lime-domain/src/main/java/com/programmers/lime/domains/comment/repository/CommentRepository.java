package com.programmers.lime.domains.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.lime.domains.comment.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryForCursor {
	int countByFeedId(final Long feedId);
}
