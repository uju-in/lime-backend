package com.programmers.bucketback.domains.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -199271506L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final com.programmers.bucketback.domains.QBaseEntity _super = new com.programmers.bucketback.domains.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.programmers.bucketback.domains.member.domain.vo.QIntroduction introduction;

    public final NumberPath<Integer> levelPoint = createNumber("levelPoint", Integer.class);

    public final com.programmers.bucketback.domains.member.domain.vo.QLoginInfo loginInfo;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final com.programmers.bucketback.domains.member.domain.vo.QNickname nickname;

    public final EnumPath<com.programmers.bucketback.domains.member.domain.vo.Role> role = createEnum("role", com.programmers.bucketback.domains.member.domain.vo.Role.class);

    public final EnumPath<com.programmers.bucketback.domains.member.domain.vo.MemberStatus> status = createEnum("status", com.programmers.bucketback.domains.member.domain.vo.MemberStatus.class);

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.introduction = inits.isInitialized("introduction") ? new com.programmers.bucketback.domains.member.domain.vo.QIntroduction(forProperty("introduction")) : null;
        this.loginInfo = inits.isInitialized("loginInfo") ? new com.programmers.bucketback.domains.member.domain.vo.QLoginInfo(forProperty("loginInfo")) : null;
        this.nickname = inits.isInitialized("nickname") ? new com.programmers.bucketback.domains.member.domain.vo.QNickname(forProperty("nickname")) : null;
    }

}

