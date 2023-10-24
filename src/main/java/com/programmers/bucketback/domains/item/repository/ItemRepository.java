package com.programmers.bucketback.domains.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.bucketback.domains.item.domain.Item;

public interface ItemRepository extends JpaRepository<Item,Long> {
}
