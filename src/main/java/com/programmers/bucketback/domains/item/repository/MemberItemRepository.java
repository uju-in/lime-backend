package com.programmers.bucketback.domains.item.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.domain.MemberItem;

public interface MemberItemRepository extends JpaRepository<MemberItem, Long> {

	boolean existsMemberItemByMembersIdAndItem(Long memberId, Item item);

	Optional<MemberItem> findMemberItemByMembersIdAndItem(Long memberId, Item item);
}
