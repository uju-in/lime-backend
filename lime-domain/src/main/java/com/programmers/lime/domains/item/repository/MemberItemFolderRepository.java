package com.programmers.lime.domains.item.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.programmers.lime.domains.item.domain.MemberItemFolder;

public interface MemberItemFolderRepository extends JpaRepository<MemberItemFolder, Long> {

	boolean existsMemberItemFolderByIdAndMemberId(Long id, Long memberId);

	List<MemberItemFolder> findMemberItemFoldersByMemberId(Long memberId);

	@Query("SELECT mif.id FROM MemberItemFolder mif WHERE mif.memberId = :memberId AND mif.name = :name")
	Optional<Long> getIdByMemberIdAndName(Long memberId, String name);
}
