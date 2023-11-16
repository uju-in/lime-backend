package com.programmers.bucketback.domains.feed.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFeedItem is a Querydsl query type for FeedItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFeedItem extends EntityPathBase<FeedItem> {

    private static final long serialVersionUID = 1054164073L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFeedItem feedItem = new QFeedItem("feedItem");

    public final QFeed feed;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.programmers.bucketback.domains.item.domain.QItem item;

    public QFeedItem(String variable) {
        this(FeedItem.class, forVariable(variable), INITS);
    }

    public QFeedItem(Path<? extends FeedItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFeedItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFeedItem(PathMetadata metadata, PathInits inits) {
        this(FeedItem.class, metadata, inits);
    }

    public QFeedItem(Class<? extends FeedItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.feed = inits.isInitialized("feed") ? new QFeed(forProperty("feed"), inits.get("feed")) : null;
        this.item = inits.isInitialized("item") ? new com.programmers.bucketback.domains.item.domain.QItem(forProperty("item")) : null;
    }

}

