package com.programmers.bucketback.domains.bucket.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBucket is a Querydsl query type for Bucket
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBucket extends EntityPathBase<Bucket> {

    private static final long serialVersionUID = -1528443314L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBucket bucket = new QBucket("bucket");

    public final com.programmers.bucketback.domains.QBaseEntity _super = new com.programmers.bucketback.domains.QBaseEntity(this);

    public final QBucketInfo bucketInfo;

    public final ListPath<BucketItem, QBucketItem> bucketItems = this.<BucketItem, QBucketItem>createList("bucketItems", BucketItem.class, QBucketItem.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QBucket(String variable) {
        this(Bucket.class, forVariable(variable), INITS);
    }

    public QBucket(Path<? extends Bucket> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBucket(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBucket(PathMetadata metadata, PathInits inits) {
        this(Bucket.class, metadata, inits);
    }

    public QBucket(Class<? extends Bucket> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bucketInfo = inits.isInitialized("bucketInfo") ? new QBucketInfo(forProperty("bucketInfo")) : null;
    }

}

