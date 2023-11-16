package com.programmers.bucketback.domains.member.domain.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QIntroduction is a Querydsl query type for Introduction
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QIntroduction extends BeanPath<Introduction> {

    private static final long serialVersionUID = -720327973L;

    public static final QIntroduction introduction1 = new QIntroduction("introduction1");

    public final StringPath introduction = createString("introduction");

    public QIntroduction(String variable) {
        super(Introduction.class, forVariable(variable));
    }

    public QIntroduction(Path<? extends Introduction> path) {
        super(path.getType(), path.getMetadata());
    }

    public QIntroduction(PathMetadata metadata) {
        super(Introduction.class, metadata);
    }

}

