package com.hcl.cloud.uaa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.cloud.uaa.bean.JwtToken;
import com.hcl.cloud.uaa.repository.JwtTokenRepository;
import com.hcl.cloud.uaa.security.JwtTokenProvider;
import com.hcl.cloud.uaa.service.ITokenService;

@Service
public class TokenServiceImpl implements ITokenService {

	@Autowired
	private JwtTokenRepository jwtTokenRepository;

	@Override
	public JwtToken getInfoByEmail(String email) {
		JwtToken tokenInfo = jwtTokenRepository.findByEmail(email);
		return tokenInfo;

	}

	@Override
	public JwtToken getInfoByToken(String token) {
		JwtToken tokenInfo = jwtTokenRepository.findBytoken(token);
		return tokenInfo;

	}

	public JwtTokenRepository getJwtTokenRepository() {
		return jwtTokenRepository;
	}

	public void setJwtTokenRepository(JwtTokenRepository jwtTokenRepository) {
		this.jwtTokenRepository = jwtTokenRepository;
	}
	
	

}
