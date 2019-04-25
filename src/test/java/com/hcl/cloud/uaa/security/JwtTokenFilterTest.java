package com.hcl.cloud.uaa.security;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.security.core.Authentication;

public class JwtTokenFilterTest {
	
	private JwtTokenProvider jwtTokenProvider;
	HttpServletRequest request;
	HttpServletResponse response;
	FilterChain filterChain;
	Authentication auth;
	private String token = "adfadf.asdfds.dafsa"; 
	
	JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(jwtTokenProvider);
	
	@Before
	public void init() {
		jwtTokenProvider = PowerMockito.mock(JwtTokenProvider.class);
		request = PowerMockito.mock(HttpServletRequest.class);
	    response = PowerMockito.mock(HttpServletResponse.class);
	    filterChain = PowerMockito.mock(FilterChain.class);
	    auth = PowerMockito.mock(Authentication.class);
	}

	/*@Test
	public void doFilterTest() throws IOException, ServletException {
		
		jwtTokenFilter.setJwtTokenProvider(jwtTokenProvider);
		PowerMockito.when(jwtTokenProvider.resolveToken((HttpServletRequest) request)).thenReturn(token);
		PowerMockito.when(jwtTokenProvider.isTokenPresentInDB(token)).thenReturn(true);
		PowerMockito.when(jwtTokenProvider.validateToken(token)).thenReturn(true);
		PowerMockito.when(jwtTokenProvider.getAuthentication(token)).thenReturn(auth);
		jwtTokenFilter.doFilter(request, response, filterChain);
		assertNotNull(auth);
	}*/

}
