package com.programmers.bucketback.domains.item.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.domain.MemberItem;

public interface MemberItemRepository extends JpaRepository<MemberItem, Long>, MemberItemRepositoryForCursor {

	boolean existsMemberItemByMemberIdAndItem(Long memberId, Item item);

	Optional<MemberItem> findMemberItemByMemberIdAndItem(Long memberId, Item item);

	List<MemberItem> findByMemberId(Long memberId);

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
}
