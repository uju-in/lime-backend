package com.programmers.lime.domains.item.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.item.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryForCursor {

	List<Item> findItemsByNameContains(String name);

	boolean existsItemsByUrl(String url);

	@Query("select count(i) from Item i where i.name like %:keyword% and (i.hobby = :hobby or :hobby is null)")
	int countItemByKeywordAndHobby(@Param("keyword") String keyword, @Param("hobby") Hobby hobby);

	boolean existsAllByIdIn(List<Long> itemIds);

	List<Item> findAllByIdIn(List<Long> itemIds);
}
