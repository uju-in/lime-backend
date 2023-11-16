package com.programmers.bucketback.domains.inventory.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInventoryItem is a Querydsl query type for InventoryItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInventoryItem extends EntityPathBase<InventoryItem> {

    private static final long serialVersionUID = 1028674421L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInventoryItem inventoryItem = new QInventoryItem("inventoryItem");

    public final com.programmers.bucketback.domains.QBaseEntity _super = new com.programmers.bucketback.domains.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QInventory inventory;

    public final com.programmers.bucketback.domains.item.domain.QItem item;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QInventoryItem(String variable) {
        this(InventoryItem.class, forVariable(variable), INITS);
    }

    public QInventoryItem(Path<? extends InventoryItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInventoryItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInventoryItem(PathMetadata metadata, PathInits inits) {
        this(InventoryItem.class, metadata, inits);
    }

    public QInventoryItem(Class<? extends InventoryItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.inventory = inits.isInitialized("inventory") ? new QInventory(forProperty("inventory")) : null;
        this.item = inits.isInitialized("item") ? new com.programmers.bucketback.domains.item.domain.QItem(forProperty("item")) : null;
    }

}

