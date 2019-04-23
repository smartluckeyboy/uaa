/**
 * 
 */
package com.hcl.cloud.uaa.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.ResponseEntity;

import com.hcl.cloud.uaa.bean.AuthTokenResponse;
import com.hcl.cloud.uaa.bean.JwtToken;
import com.hcl.cloud.uaa.controller.TokenDecoderController;
import com.hcl.cloud.uaa.service.impl.TokenServiceImpl;

@RunWith(PowerMockRunner.class)
public class TokenDecoderTest {
	
	TokenDecoderController tokenDecoder = new TokenDecoderController();
	TokenServiceImpl tokenServiceImpl;
	String token = "lkdfi.kljdflakj12.jkdlasf12lkjd";
	
	@Before
	public void init() {
		tokenServiceImpl = PowerMockito.mock(TokenServiceImpl.class);
	}
	
	
	@Test
	public void getCartTest() {
		tokenDecoder.setiTokenService(tokenServiceImpl);
		JwtToken jwtToken = new JwtToken();
		jwtToken.setEmail("anushav@hcl.com");
		jwtToken.setId(1234567);
		jwtToken.setToken("lkdfi.kljdflakj12.jkdlasf12lkjd");
		jwtToken.setUserId("1234567");
		PowerMockito.when(tokenServiceImpl.getInfoByToken(token)).thenReturn(jwtToken);
		ResponseEntity<AuthTokenResponse> res = tokenDecoder.getCart(token);
		assertEquals(jwtToken.getUserId(), res.getBody().getUserId());
		
	}

}
