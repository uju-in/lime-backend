package com.programmers.bucketback.domains.member.application;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.member.application.dto.request.LoginMemberServiceRequest;
import com.programmers.bucketback.domains.member.application.dto.request.SignupMemberServiceRequest;
import com.programmers.bucketback.domains.member.application.dto.response.LoginMemberServiceResponse;
import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.domains.member.domain.MemberSecurity;
import com.programmers.bucketback.domains.member.domain.Role;
import com.programmers.bucketback.domains.member.repository.MemberRepository;
import com.programmers.bucketback.global.config.security.jwt.JwtService;
import com.programmers.bucketback.global.error.exception.EntityNotFoundException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public void signup(final SignupMemberServiceRequest request) {
		Member member = Member.builder()
			.email(request.email())
			.password(passwordEncoder.encode(request.password()))
			.nickname(request.nickname())
			.role(Role.USER)
			.build();

		memberRepository.save(member);
	}

	 public LoginMemberServiceResponse login(LoginMemberServiceRequest request) {
		 Member member = memberRepository.findByEmail(request.email())
			 .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

		 UsernamePasswordAuthenticationToken authenticationToken =
				 new UsernamePasswordAuthenticationToken(member.getId(), request.password());
		 authenticationManager.authenticate(authenticationToken);

	 	String jwtToken = jwtService.generateToken(new MemberSecurity(member));

	 	return new LoginMemberServiceResponse(member.getNickname(), jwtToken);
	 }

	@Transactional
	public void deleteMember() {
		Long memberId = MemberUtils.getCurrentMemberId();

		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

		member.delete();
	}
}
