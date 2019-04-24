package com.hcl.cloud.uaa.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.authentication.AuthenticationManager;

import com.hcl.cloud.uaa.bean.User;
import com.hcl.cloud.uaa.exception.CustomException;
import com.hcl.cloud.uaa.repository.UserRepository;
import com.hcl.cloud.uaa.security.JwtTokenProvider;
import com.hcl.cloud.uaa.service.impl.LoginServiceImpl;


@RunWith(PowerMockRunner.class)
public class LoginServiceTest {
	
	private AuthenticationManager authenticationManager; 
	private UserRepository userRepository;
	private User user;
	private JwtTokenProvider jwtTokenProvider;

	
	LoginServiceImpl iloginServiceImpl = new LoginServiceImpl();
	
	String token = "lkdfi.kljdflakj12.jkdlasf12lkjd";
	
	@Before
	public void setupPowerMock() {
		authenticationManager = PowerMockito.mock(AuthenticationManager.class);
		userRepository = PowerMockito.mock(UserRepository.class);
		jwtTokenProvider = PowerMockito.mock(JwtTokenProvider.class);
	}
	
	@Test
	public void testLogin(){
		user = new User();
		user.setEmail("harit@hcl.com");
		user.setActive(1);
		user.setEnabled(true);
		user.setExpired(false);
		user.setId(1L);
		user.setLastName("sah");
		user.setLoacked(false);
		user.setUserName("9355cae5-a082-409d-8aaa-f9556982d767");
		user.setPassword("$2a$12$VNW1nJ5XTdw8nxYlqUs/ZOPwxfP2eHsmNOyn.XzUFNiEL94XyODeS");
		user.setRole("admin");
		
		PowerMockito.when(userRepository.findByEmail("harit@hcl.com")).thenReturn(user);
		PowerMockito.when(jwtTokenProvider.createToken(user)).thenReturn(token);
		
		iloginServiceImpl.setAuthenticationManager(authenticationManager);
		iloginServiceImpl.setUserRepository(userRepository);
		iloginServiceImpl.setJwtTokenProvider(jwtTokenProvider);
		String accessToken = iloginServiceImpl.login("harit@hcl.com", "1234");
		assertEquals(token, accessToken);
	}
	
	@Test(expected=CustomException.class)
	public void testLoginException() {
		user = null;
		iloginServiceImpl.setAuthenticationManager(authenticationManager);
		iloginServiceImpl.setUserRepository(userRepository);
		PowerMockito.when(userRepository.findByEmail("harit@hcl.com")).thenReturn(user);
		iloginServiceImpl.login("harit@hcl.com", "1234");
	}
}
