package com.programmers.bucketback.domains.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.bucketback.domains.comment.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryForCursor {
}
