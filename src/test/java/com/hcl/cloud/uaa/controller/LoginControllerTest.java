package com.hcl.cloud.uaa.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.ResponseEntity;

import com.hcl.cloud.uaa.bean.AuthResponse;
import com.hcl.cloud.uaa.bean.LoginRequest;
import com.hcl.cloud.uaa.controller.LoginController;
import com.hcl.cloud.uaa.service.ILoginService;
import com.hcl.cloud.uaa.service.impl.LoginServiceImpl;

@RunWith(PowerMockRunner.class)
public class LoginControllerTest {
	
	LoginController controller = new LoginController();
	LoginServiceImpl loginService;
	ILoginService iLoginService;
	String token = "lkdfi.kljdflakj12.jkdlasf12lkjd";
	
	@Before
	public void init() {
		loginService = PowerMockito.mock(LoginServiceImpl.class);
	}
	
	@Test
	public void loginTest() {
		String username = "harit@hcl.com";
		String password = "123";
		LoginRequest loginRequest = new LoginRequest(username, password);
		controller.setiLoginService(loginService);
		PowerMockito.when(loginService.login(loginRequest.getUsername(),loginRequest.getPassword())).thenReturn(token);
		ResponseEntity<AuthResponse> res =controller.login(loginRequest);
		assertEquals(token, res.getBody().getAccessToken());
		
	}
}
