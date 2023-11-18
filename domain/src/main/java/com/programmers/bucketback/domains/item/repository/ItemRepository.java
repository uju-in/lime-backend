package com.programmers.bucketback.domains.item.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.bucketback.domains.item.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryForCursor {

	List<Item> findItemsByNameContains(String name);

	boolean existsItemsByUrl(String url);
}
