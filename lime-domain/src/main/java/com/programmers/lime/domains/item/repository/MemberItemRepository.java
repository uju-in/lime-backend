package com.programmers.lime.domains.item.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.item.domain.Item;
import com.programmers.lime.domains.item.domain.MemberItem;

public interface MemberItemRepository extends JpaRepository<MemberItem, Long>, MemberItemRepositoryForCursor {

	boolean existsMemberItemByMemberIdAndItem(Long memberId, Item item);

	Optional<MemberItem> findMemberItemByMemberIdAndItem(Long memberId, Item item);

	Optional<MemberItem> findMemberItemByFolderIdAndItem(Long folderId, Item item);

	List<MemberItem> findByMemberId(Long memberId);

	List<MemberItem> findByFolderIdAndMemberId(Long folderId, Long memberId);

	int countByItemId(Long itemId);

	@Query(
		"""
				SELECT
					CASE WHEN COUNT(mi) > 0
					THEN true
					ELSE false
					END
				FROM MemberItem mi
				WHERE
					mi.memberId = :memberId AND mi.item IN :items
			"""
	)
	boolean existsByMemberIdAndItemsIn(@Param("memberId") Long memberId, @Param("items") List<Item> items);

	int countByMemberId(final Long memberId);

	@Query(
		"""
				SELECT COUNT(mi)
			  	FROM MemberItem mi
			  	JOIN mi.item i
			  	WHERE i.hobby = :hobby AND mi.memberId = :memberId
			"""
	)
	int countByHobbyAndMemberId(
		final Hobby hobby,
		final Long memberId
	);

	boolean existsMemberItemByIdAndMemberId(Long memberItemId, Long memberId);

	List<MemberItem> findByFolderId(Long folderId);
}
