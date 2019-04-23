package com.hcl.cloud.uaa.controller;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import com.hcl.cloud.uaa.bean.JwtToken;
import com.hcl.cloud.uaa.bean.User;
import com.hcl.cloud.uaa.repository.JwtTokenRepository;
import com.hcl.cloud.uaa.security.JwtTokenProvider;
import com.hcl.cloud.uaa.service.impl.TokenServiceImpl;

public class JwtTokenProviderTest {

	private TokenServiceImpl tokenServiceImpl;
	private JwtTokenRepository jwtTokenRepository;
	JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

	User user = new User();
	JwtToken jwtToken = new JwtToken();

	@Before
	public void init() {
		tokenServiceImpl = PowerMockito.mock(TokenServiceImpl.class);
		jwtTokenRepository = PowerMockito.mock(JwtTokenRepository.class);
	}

	@Test
	public void createTokenTest() {
		jwtTokenProvider.setiTokenService(tokenServiceImpl);
		jwtTokenProvider.setJwtTokenRepository(jwtTokenRepository);
		String token = "ahfjhgi.hfugug.jgfuhgjkh";
		jwtToken.setToken(token);
		user.setEmail("anushav@hcl.com");
		user.setPassword("123");
		
		PowerMockito.when(tokenServiceImpl.getInfoByEmail(user.getEmail())).thenReturn(jwtToken);
		String tokenNew = jwtTokenProvider.createToken(user);
		assertNotNull(tokenNew);
	}

}
