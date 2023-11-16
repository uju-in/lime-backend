package com.programmers.bucketback.domains.vote.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVoter is a Querydsl query type for Voter
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVoter extends EntityPathBase<Voter> {

    private static final long serialVersionUID = 515010916L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVoter voter = new QVoter("voter");

    public final com.programmers.bucketback.domains.QBaseEntity _super = new com.programmers.bucketback.domains.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> itemId = createNumber("itemId", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QVote vote;

    public QVoter(String variable) {
        this(Voter.class, forVariable(variable), INITS);
    }

    public QVoter(Path<? extends Voter> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVoter(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVoter(PathMetadata metadata, PathInits inits) {
        this(Voter.class, metadata, inits);
    }

    public QVoter(Class<? extends Voter> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.vote = inits.isInitialized("vote") ? new QVote(forProperty("vote"), inits.get("vote")) : null;
    }

}

