package com.programmers.bucketback.domains.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.bucketback.domains.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByLoginInfoEmail(final String email);

	boolean existsByNickname(final String nickname);

	Optional<Member> findByNickname(final String nickname);

	boolean existsByLoginInfoEmail(final String email);
}
