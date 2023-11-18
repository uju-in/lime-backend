package com.programmers.bucketback.global.level;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.domains.member.implementation.MemberAppender;
import com.programmers.bucketback.domains.member.implementation.MemberReader;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class PointManagerAop {

	private final MemberReader memberReader;
	private final MemberAppender memberAppender;

	@AfterReturning(value = "@annotation(com.programmers.bucketback.global.level.PayPoint)", returning = "memberId")
	public void payPoint(
		final JoinPoint joinPoint,
		final Long memberId
	) {
		final int point = getPoint(joinPoint);
		final Member member = memberReader.read(memberId);
		member.earnPoint(point);
		memberAppender.append(member);
	}

	private int getPoint(final JoinPoint joinPoint) {
		final MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		final Method method = signature.getMethod();
		final PayPoint payPoint = method.getDeclaredAnnotation(PayPoint.class);
		return payPoint.value();
	}
}
