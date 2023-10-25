package com.programmers.bucketback.domains.member.application;

import com.programmers.bucketback.domains.member.api.dto.response.MemberLoginResponse;
import com.programmers.bucketback.domains.member.application.dto.request.LoginMemberServiceRequest;
import com.programmers.bucketback.domains.member.application.dto.request.SignupMemberServiceRequest;
import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.domains.member.domain.MemberSecurity;
import com.programmers.bucketback.domains.member.domain.Role;
import com.programmers.bucketback.domains.member.repository.MemberRepository;
import com.programmers.bucketback.global.config.security.jwt.JwtService;
import com.programmers.bucketback.global.error.exception.EntityNotFoundException;
import com.programmers.bucketback.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

	 public MemberLoginResponse login(LoginMemberServiceRequest request) {
		 UsernamePasswordAuthenticationToken authenticationToken =
				 new UsernamePasswordAuthenticationToken(request.email(), request.password());
		 authenticationManager.authenticate(authenticationToken);

	 	Member member = memberRepository.findByEmail(request.email())
	 		.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

	 	String jwtToken = jwtService.generateToken(new MemberSecurity(member));

	 	return new MemberLoginResponse(member.getNickname(), jwtToken);
	 }
}
