package com.programmers.lime.domains.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.item.domain.MemberItemFolder;

public interface MemberItemFolderRepository
	extends JpaRepository<MemberItemFolder, Long>, MemberItemFolderRepositoryForCursor {

	int countByHobbyAndMemberId(
		final Hobby hobby,
		final Long memberId
	);

	boolean existsMemberItemFolderByIdAndMemberId(Long id, Long memberId);
}
