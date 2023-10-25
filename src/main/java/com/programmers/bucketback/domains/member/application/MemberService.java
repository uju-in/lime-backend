package com.programmers.bucketback.domains.member.application;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.member.application.dto.request.SignupMemberServiceRequest;
import com.programmers.bucketback.domains.member.domain.MemberSecurity;
import com.programmers.bucketback.domains.member.domain.Role;
import com.programmers.bucketback.domains.member.repository.MemberRepository;
import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.global.config.security.jwt.JwtService;

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

	// public MemberResponse authenticate(MemberRequest request) {
	// 	authenticationManager.authenticate(
	// 		new UsernamePasswordAuthenticationToken(
	// 			request.email(),
	// 			request.password()
	// 		)
	// 	);
	// 	Member member = memberRepository.findByEmail(request.email())
	// 		.orElseThrow();
	// 	String jwtToken = jwtService.generateToken(new MemberSecurity(member));
	// 	return new MemberResponse(jwtToken);
	// }
}
