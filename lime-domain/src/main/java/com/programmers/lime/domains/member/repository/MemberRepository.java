package com.programmers.lime.domains.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.member.domain.vo.SocialType;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findBySocialInfoEmail(final String email);

	boolean existsByNicknameNickname(final String nickname);

	Optional<Member> findByNicknameNickname(final String nickname);

	Optional<Member> findBySocialInfoSocialIdAndSocialInfoSocialType(
		final Long socialId,
		final SocialType socialType
	);
}
