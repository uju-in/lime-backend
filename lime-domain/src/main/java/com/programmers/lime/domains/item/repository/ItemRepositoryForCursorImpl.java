package com.programmers.lime.domains.item.repository;

import static com.programmers.lime.domains.item.domain.QItem.*;
import static com.programmers.lime.domains.item.domain.QMemberItem.*;
import static com.programmers.lime.domains.review.domain.QReview.*;
import static com.querydsl.core.group.GroupBy.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.inventory.model.InventoryReviewItemSummary;
import com.programmers.lime.domains.item.model.FavoriteInfoForItemSummary;
import com.programmers.lime.domains.item.model.ItemCursorIdInfo;
import com.programmers.lime.domains.item.model.ItemInfo;
import com.programmers.lime.domains.item.model.ItemInfoForItemSummary;
import com.programmers.lime.domains.item.model.ItemSortCondition;
import com.programmers.lime.domains.item.model.ReviewInfoForItemSummary;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ItemRepositoryForCursorImpl implements ItemRepositoryForCursor {

	private final JPAQueryFactory jpaQueryFactory;

	private static final Pattern DIGIT_PATTERN = Pattern.compile("-?\\d*");

	@Override
	public List<ItemCursorIdInfo> getItemIdsByCursor(
		final String keyword,
		final String cursorId,
		final int pageSize,
		final ItemSortCondition itemSortCondition,
		final Hobby hobby
	) {
		return jpaQueryFactory
			.select(
				Projections.constructor(
					ItemCursorIdInfo.class,
					item.id,
					generateCursorId(itemSortCondition)
				)
			).from(item)
			.where(
				eqKeyword(keyword),
				hobbyCondition(hobby),
				whereCursorIdCondition(cursorId, itemSortCondition)
			).orderBy(orderBySortCondition(itemSortCondition), item.id.desc())
			.groupBy(item.id)
			.having(
				havingCursorIdCondition(cursorId, itemSortCondition)
			)
			.leftJoin(review).on(item.id.eq(review.itemId))
			.limit(pageSize)
			.fetch();
	}

	private BooleanExpression eqKeyword(final String keyword) {
		return keyword != null ? item.name.contains(keyword) : null;
	}

	@Override
	public List<ItemInfoForItemSummary> getItemInfosByItemIds(final List<Long> itemIds) {
		return jpaQueryFactory
			.select(
				Projections.constructor(
					ItemInfoForItemSummary.class,
					item.id,
					item.name,
					item.price,
					item.image
				)
			).from(item)
			.where(
				item.id.in(itemIds)
			).fetch();
	}

	@Override
	public List<ReviewInfoForItemSummary> getReviewInfosByItemIds(final List<Long> itemIds) {
		return jpaQueryFactory
			.select(
				Projections.constructor(ReviewInfoForItemSummary.class,
					item.id,
					review.id.count()
				)
			).from(item)
			.where(
				item.id.in(itemIds)
			).leftJoin(review).on(item.id.eq(review.itemId))
			.groupBy(item.id)
			.fetch();
	}

	@Override
	public List<FavoriteInfoForItemSummary> getFavoriteInfosByItemIds(final List<Long> itemIds) {
		return jpaQueryFactory
			.select(
				Projections.constructor(FavoriteInfoForItemSummary.class,
					item.id,
					memberItem.id.count()
				)
			).from(item)
			.where(
				item.id.in(itemIds)
			).leftJoin(memberItem).on(item.eq(memberItem.item))
			.groupBy(item.id)
			.fetch();
	}

	@Override
	public List<InventoryReviewItemSummary> findReviewedItemByCursor(
		final List<Long> itemIdsFromReview,
		final List<Long> itemIdsFromInventory,
		final Hobby hobby,
		final String cursorId,
		final int pageSize
	) {
		return jpaQueryFactory
			.selectFrom(item)
			.where(
				whereCursorIdCondition(cursorId, null),
				item.id.in(itemIdsFromReview),
				hobbyCondition(hobby)
			)
			.orderBy(decrease(), item.id.desc())
			.limit(pageSize)
			.transform(groupBy(item.id)
				.list(Projections.constructor(InventoryReviewItemSummary.class,
					generateItemCursorId(),
					isSelected(itemIdsFromInventory),
					item.createdAt,
					Projections.constructor(ItemInfo.class,
						item.id,
						item.name,
						item.price,
						item.image
					))
				)
			);
	}

	private Expression<Boolean> isSelected(final List<Long> itemIdsFromInventory) {
		if (itemIdsFromInventory == null) {
			return Expressions.constant(false);
		}

		return new CaseBuilder()
			.when(item.id.in(itemIdsFromInventory))
			.then(true)
			.otherwise(false);
	}

	private OrderSpecifier<?> orderBySortCondition(
		final ItemSortCondition itemSortCondition
	) {

		if (itemSortCondition == null)
			return new OrderSpecifier<>(Order.DESC, item.createdAt);

		return switch (itemSortCondition) {
			case REVIEW_COUNT_DESC -> new OrderSpecifier<>(Order.DESC, review.count());
			case REVIEW_RATING_DESC -> new OrderSpecifier<>(Order.DESC, review.rating.avg());
			case PRICE_LOW_TO_HIGH -> new OrderSpecifier<>(Order.ASC, item.price);
			case PRICE_HIGH_TO_LOW -> new OrderSpecifier<>(Order.DESC, item.price);
			default -> new OrderSpecifier<>(Order.DESC, item.createdAt);
		};
	}

	private OrderSpecifier<LocalDateTime> decrease() {
		return new OrderSpecifier<>(Order.DESC, item.createdAt);
	}

	private BooleanExpression hobbyCondition(final Hobby hobby) {
		if (hobby == null) {
			return null;
		}

		return item.hobby.eq(hobby);
	}

	private BooleanExpression whereCursorIdCondition(
		final String cursorId,
		final ItemSortCondition itemSortCondition
	) {
		if (Objects.isNull(cursorId) || !DIGIT_PATTERN.matcher(cursorId).matches()) {
			return null;
		}

		if (itemSortCondition == ItemSortCondition.NEWEST ||
			itemSortCondition == ItemSortCondition.PRICE_HIGH_TO_LOW ||
			itemSortCondition == null
		) {
			return generateCursorId(itemSortCondition)
				.lt(cursorId);
		}

		if (itemSortCondition == ItemSortCondition.PRICE_LOW_TO_HIGH) {
			return generateCursorId(itemSortCondition)
				.gt(cursorId);
		}

		return null;
	}

	private BooleanExpression havingCursorIdCondition(
		final String cursorId,
		final ItemSortCondition itemSortCondition
	) {
		if (Objects.isNull(cursorId) || !DIGIT_PATTERN.matcher(cursorId).matches()) {
			return null;
		}

		if (
			itemSortCondition == ItemSortCondition.REVIEW_COUNT_DESC ||
				itemSortCondition == ItemSortCondition.REVIEW_RATING_DESC
		) {
			return generateCursorId(itemSortCondition)
				.lt(cursorId);
		}

		return null;
	}

	private StringExpression generateItemCursorId() {
		return Expressions.stringTemplate(
				"DATE_FORMAT({0}, {1})",
				item.createdAt,
				ConstantImpl.create("%Y%m%d%H%i%s"))
			.concat(StringExpressions.lpad(
				item.id.stringValue(), 8, '0'
			));
	}

	public StringExpression generateCursorId(
		final ItemSortCondition itemSortCondition
	) {
		ItemSortCondition defaultSortCondition = itemSortCondition;
		if (itemSortCondition == null) {
			defaultSortCondition = ItemSortCondition.NEWEST;
		}

		return switch (defaultSortCondition) {
			case REVIEW_RATING_DESC -> concatWithLpadZero(
				replaceStringExpression(review.rating.avg().stringValue(), ".", ""), item.id.stringValue()
			);
			case REVIEW_COUNT_DESC -> concatWithLpadZero(review.count().stringValue(), item.id.stringValue());
			case PRICE_LOW_TO_HIGH, PRICE_HIGH_TO_LOW ->
				concatWithLpadZero(item.price.stringValue(), item.id.stringValue());
			case NEWEST -> concatWithLpadZero(
				replaceStringExpression(item.createdAt.stringValue(), "-", ""), item.id.stringValue()
			);
		};
	}

	public StringExpression concatWithLpadZero(final StringExpression str1, final StringExpression str2) {
		return lpadWithZero(str1).concat(lpadWithZero(str2));
	}

	public StringExpression replaceStringExpression(
		final StringExpression originalStrExpression,
		final String originalStr,
		final String replaceStr
	) {
		return Expressions.stringTemplate(
			"replace({0}, {1}, {2})", originalStrExpression, originalStr, replaceStr
		);
	}

	public StringExpression lpadWithZero(final StringExpression stringExpression) {
		StringExpression defaultStr = Expressions.stringTemplate("coalesce({0}, '00000000')", stringExpression);
		return StringExpressions.lpad(
			defaultStr, 8, '0'
		);
	}
}