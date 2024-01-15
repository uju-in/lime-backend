package com.programmers.lime.domains.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.item.domain.MemberItemFolder;

public interface MemberItemFolderRepository
	extends JpaRepository<MemberItemFolder, Long>, MemberItemFolderRepositoryForCursor {

	@Query(
		"""
			SELECT COUNT(mi)
			FROM MemberItemFolder mi
			WHERE mi.hobby = :hobby AND mi.memberId = :memberId
		"""
	)
	int countByHobbyAndMemberId(
		final Hobby hobby,
		final Long memberId
	);
}
