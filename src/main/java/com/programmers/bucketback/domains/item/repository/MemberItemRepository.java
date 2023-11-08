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
}
