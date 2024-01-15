package com.programmers.lime.domains.friendships.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.lime.domains.friendships.domain.Friendship;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
}
