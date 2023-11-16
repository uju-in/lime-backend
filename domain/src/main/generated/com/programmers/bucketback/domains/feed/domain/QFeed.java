package com.programmers.bucketback.domains.feed.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFeed is a Querydsl query type for Feed
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFeed extends EntityPathBase<Feed> {

    private static final long serialVersionUID = -815177674L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFeed feed = new QFeed("feed");

    public final com.programmers.bucketback.domains.QBaseEntity _super = new com.programmers.bucketback.domains.QBaseEntity(this);

    public final com.programmers.bucketback.domains.bucket.domain.QBucketInfo bucketInfo;

    public final ListPath<com.programmers.bucketback.domains.comment.domain.Comment, com.programmers.bucketback.domains.comment.domain.QComment> comments = this.<com.programmers.bucketback.domains.comment.domain.Comment, com.programmers.bucketback.domains.comment.domain.QComment>createList("comments", com.programmers.bucketback.domains.comment.domain.Comment.class, com.programmers.bucketback.domains.comment.domain.QComment.class, PathInits.DIRECT2);

    public final QFeedContent content;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final ListPath<FeedItem, QFeedItem> feedItems = this.<FeedItem, QFeedItem>createList("feedItems", FeedItem.class, QFeedItem.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<FeedLike, QFeedLike> likes = this.<FeedLike, QFeedLike>createList("likes", FeedLike.class, QFeedLike.class, PathInits.DIRECT2);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QFeed(String variable) {
        this(Feed.class, forVariable(variable), INITS);
    }

    public QFeed(Path<? extends Feed> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFeed(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFeed(PathMetadata metadata, PathInits inits) {
        this(Feed.class, metadata, inits);
    }

    public QFeed(Class<? extends Feed> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bucketInfo = inits.isInitialized("bucketInfo") ? new com.programmers.bucketback.domains.bucket.domain.QBucketInfo(forProperty("bucketInfo")) : null;
        this.content = inits.isInitialized("content") ? new QFeedContent(forProperty("content")) : null;
    }

}

