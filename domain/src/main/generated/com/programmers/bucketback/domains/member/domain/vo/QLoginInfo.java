package com.programmers.bucketback.domains.member.domain.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLoginInfo is a Querydsl query type for LoginInfo
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QLoginInfo extends BeanPath<LoginInfo> {

    private static final long serialVersionUID = -1625393002L;

    public static final QLoginInfo loginInfo = new QLoginInfo("loginInfo");

    public final StringPath email = createString("email");

    public final StringPath password = createString("password");

    public QLoginInfo(String variable) {
        super(LoginInfo.class, forVariable(variable));
    }

    public QLoginInfo(Path<? extends LoginInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLoginInfo(PathMetadata metadata) {
        super(LoginInfo.class, metadata);
    }

}

