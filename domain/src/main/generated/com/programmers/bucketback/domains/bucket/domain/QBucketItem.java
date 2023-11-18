package com.programmers.bucketback.domains.bucket.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBucketItem is a Querydsl query type for BucketItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBucketItem extends EntityPathBase<BucketItem> {

    private static final long serialVersionUID = 96265857L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBucketItem bucketItem = new QBucketItem("bucketItem");

    public final com.programmers.bucketback.domains.QBaseEntity _super = new com.programmers.bucketback.domains.QBaseEntity(this);

    public final QBucket bucket;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.programmers.bucketback.domains.item.domain.QItem item;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QBucketItem(String variable) {
        this(BucketItem.class, forVariable(variable), INITS);
    }

    public QBucketItem(Path<? extends BucketItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBucketItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBucketItem(PathMetadata metadata, PathInits inits) {
        this(BucketItem.class, metadata, inits);
    }

    public QBucketItem(Class<? extends BucketItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bucket = inits.isInitialized("bucket") ? new QBucket(forProperty("bucket"), inits.get("bucket")) : null;
        this.item = inits.isInitialized("item") ? new com.programmers.bucketback.domains.item.domain.QItem(forProperty("item")) : null;
    }

}

