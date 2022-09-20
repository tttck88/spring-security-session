package com.example.springsecuritysession.service;

import com.example.springsecuritysession.UserRepository;
import com.example.springsecuritysession.exception.UserNotFoundException;
import com.example.springsecuritysession.vo.UserDetailsVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class UserDetailsService {
	private final UserRepository userRepository;

	public UserDetailsVO loadUserByUserName(String userEmail) {
		return userRepository.findByUserEmail(userEmail)
			.map(u -> new UserDetailsVO(u, Collections.singleton(new SimpleGrantedAuthority(u.getRole().getValue()))))
			.orElseThrow(() -> new UserNotFoundException(userEmail));
	}
}
