package com.programmers.bucketback.global.level;

import com.programmers.bucketback.domains.member.application.MemberReader;
import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.domains.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
public class PointManager {

    private final MemberReader memberReader;
    private final MemberRepository memberRepository;

    @AfterReturning(value = "@annotation(PayPoint)", returning = "memberId")
    public void payPoint(
            final JoinPoint joinPoint,
            final Long memberId
    ) {
        final int point = getPoint(joinPoint);
        final Member member = memberReader.read(memberId);
        member.earnPoint(point);
        memberRepository.save(member);
    }

    private int getPoint(final JoinPoint joinPoint) {
        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        final Method method = signature.getMethod();
        final PayPoint payPoint = method.getDeclaredAnnotation(PayPoint.class);
        return payPoint.value();
    }
}
