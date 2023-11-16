package com.programmers.bucketback.domains.feed.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFeedContent is a Querydsl query type for FeedContent
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QFeedContent extends BeanPath<FeedContent> {

    private static final long serialVersionUID = -1363549341L;

    public static final QFeedContent feedContent = new QFeedContent("feedContent");

    public final StringPath content = createString("content");

    public QFeedContent(String variable) {
        super(FeedContent.class, forVariable(variable));
    }

    public QFeedContent(Path<? extends FeedContent> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFeedContent(PathMetadata metadata) {
        super(FeedContent.class, metadata);
    }

}

