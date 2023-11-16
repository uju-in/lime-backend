package com.programmers.bucketback.domains.inventory.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInventory is a Querydsl query type for Inventory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInventory extends EntityPathBase<Inventory> {

    private static final long serialVersionUID = 108909890L;

    public static final QInventory inventory = new QInventory("inventory");

    public final com.programmers.bucketback.domains.QBaseEntity _super = new com.programmers.bucketback.domains.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final EnumPath<com.programmers.bucketback.Hobby> hobby = createEnum("hobby", com.programmers.bucketback.Hobby.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<InventoryItem, QInventoryItem> inventoryItems = this.<InventoryItem, QInventoryItem>createList("inventoryItems", InventoryItem.class, QInventoryItem.class, PathInits.DIRECT2);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QInventory(String variable) {
        super(Inventory.class, forVariable(variable));
    }

    public QInventory(Path<? extends Inventory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInventory(PathMetadata metadata) {
        super(Inventory.class, metadata);
    }

}

