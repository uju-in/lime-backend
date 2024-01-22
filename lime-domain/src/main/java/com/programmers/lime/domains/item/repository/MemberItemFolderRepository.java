package com.programmers.lime.domains.item.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.lime.domains.item.domain.MemberItemFolder;

public interface MemberItemFolderRepository extends JpaRepository<MemberItemFolder, Long> {

	boolean existsMemberItemFolderByIdAndMemberId(Long id, Long memberId);

	List<MemberItemFolder> findMemberItemFoldersByMemberId(Long memberId);
}
