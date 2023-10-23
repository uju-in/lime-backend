package com.programmers.bucketback.domains.member.application;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.member.api.dto.request.MemberRequest;
import com.programmers.bucketback.domains.member.api.dto.response.MemberResponse;
import com.programmers.bucketback.domains.member.domain.MemberSecurity;
import com.programmers.bucketback.domains.member.api.dto.request.RegisterRequest;
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

	public MemberResponse register(RegisterRequest request) {
		Member member = Member.builder()
			.firstname(request.firstname())
			.lastname(request.lastname())
			.email(request.email())
			.password(passwordEncoder.encode(request.password()))
			.role(Role.USER)
			.build();
		memberRepository.save(member);
		String jwtToken = jwtService.generateToken(new MemberSecurity(member));
		return new MemberResponse(jwtToken);
	}

	public MemberResponse authenticate(MemberRequest request) {
		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				request.email(),
				request.password()
			)
		);
		Member member = memberRepository.findByEmail(request.email())
			.orElseThrow();
		String jwtToken = jwtService.generateToken(new MemberSecurity(member));
		return new MemberResponse(jwtToken);
	}
}
