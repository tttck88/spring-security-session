package com.example.springsecuritysession.config;

import com.example.springsecuritysession.service.UserDetailsService;
import com.example.springsecuritysession.vo.UserDetailsVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@Log4j2
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private final UserDetailsService userDetailsService;
	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
		String userEmail = token.getName();
		String userPw = (String) token.getCredentials();

		UserDetailsVO userDetailsVO = (UserDetailsVO) userDetailsService.loadUserByUserName(userEmail);

		if (!passwordEncoder.matches(userPw, userDetailsVO.getPassword())) {
			throw new BadCredentialsException(userDetailsVO.getUsername() + "Invalid password");
		}

		return new UsernamePasswordAuthenticationToken(userDetailsVO, userPw, userDetailsVO.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
