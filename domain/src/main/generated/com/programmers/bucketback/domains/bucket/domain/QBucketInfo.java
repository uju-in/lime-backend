package com.programmers.bucketback.domains.bucket.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBucketInfo is a Querydsl query type for BucketInfo
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QBucketInfo extends BeanPath<BucketInfo> {

    private static final long serialVersionUID = 96260124L;

    public static final QBucketInfo bucketInfo = new QBucketInfo("bucketInfo");

    public final NumberPath<Integer> budget = createNumber("budget", Integer.class);

    public final EnumPath<com.programmers.bucketback.Hobby> hobby = createEnum("hobby", com.programmers.bucketback.Hobby.class);

    public final StringPath name = createString("name");

    public QBucketInfo(String variable) {
        super(BucketInfo.class, forVariable(variable));
    }

    public QBucketInfo(Path<? extends BucketInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBucketInfo(PathMetadata metadata) {
        super(BucketInfo.class, metadata);
    }

}

