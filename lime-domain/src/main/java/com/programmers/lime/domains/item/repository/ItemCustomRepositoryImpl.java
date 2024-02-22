package com.programmers.lime.domains.item.repository;


import static com.programmers.lime.domains.item.domain.QItem.*;

import org.springframework.stereotype.Repository;

import com.programmers.lime.common.model.Hobby;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ItemCustomRepositoryImpl implements ItemCustomRepository{

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Long countItem(final String keyword, final Hobby hobby) {
		return jpaQueryFactory.selectFrom(item)
			.where(
				eqName(keyword),
				eqHobby(hobby)
			).fetchCount();
	}

	private BooleanExpression eqName(final String keyword) {
		return keyword != null ? item.name.contains(keyword) : null;
	}

	private BooleanExpression eqHobby(final Hobby hobby) {
		return hobby != null ? item.hobby.eq(hobby) : null;
	}
}
